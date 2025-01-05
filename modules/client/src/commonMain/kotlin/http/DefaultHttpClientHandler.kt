package org.nirmato.ollama.client.http

import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.TooManyRequests
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnsupportedMediaType
import io.ktor.util.reflect.TypeInfo
import org.nirmato.ollama.api.AuthenticationException
import org.nirmato.ollama.api.GenericIOException
import org.nirmato.ollama.api.InvalidRequestException
import org.nirmato.ollama.api.OllamaClientException
import org.nirmato.ollama.api.ResponseFailure
import org.nirmato.ollama.api.OllamaException
import org.nirmato.ollama.api.OllamaServerException
import org.nirmato.ollama.api.OllamaTimeoutException
import org.nirmato.ollama.api.PermissionException
import org.nirmato.ollama.api.RateLimitException
import org.nirmato.ollama.api.UnknownAPIException

/**
 * Default implementation of [HttpClientHandler].
 *
 * @property httpClient The HttpClient to use for performing HTTP requests.
 */
public class DefaultHttpClientHandler(private val httpClient: HttpClient) : HttpClientHandler {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun <T : Any> handle(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T = try {
        val response = httpClient.request(builder)

        when (response.status) {
            OK -> response.body<T>(info)
            else -> throw handleClientException(ClientRequestException(response, ""))
        }

    } catch (e: Exception) {
        throw handleException(e)
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun <T : Any> handle(builder: HttpRequestBuilder.() -> Unit, block: suspend (response: HttpResponse) -> T) {
        try {
            HttpStatement(builder = HttpRequestBuilder().apply(builder), client = httpClient).execute {
                when (it.status) {
                    OK -> block(it)
                    else -> throw handleClientException(ClientRequestException(it, ""))
                }
            }
        } catch (e: Exception) {
            throw handleException(e)
        }
    }

    override fun close() {
        httpClient.close()
    }

    /**
     * Handles various exceptions that can occur during an API request and converts them into appropriate [org.nirmato.ollama.api.OllamaException] instances.
     */
    private suspend fun handleException(cause: Throwable) = when (cause) {
        is CancellationException -> cause
        is ClientRequestException -> handleClientException(cause)
        is ServerResponseException -> OllamaServerException(cause)

        is HttpRequestTimeoutException,
        is SocketTimeoutException,
        is ConnectTimeoutException,
            -> OllamaTimeoutException(cause)

        is IOException -> GenericIOException(cause)
        else -> OllamaClientException(cause)
    }

    /**
     * Converts a [ClientRequestException] into a corresponding [org.nirmato.ollama.api.OllamaException] based on the HTTP status code.
     * This function helps in handling specific API errors and categorizing them into appropriate exception classes.
     */
    private suspend fun handleClientException(exception: ClientRequestException): OllamaException {
        val response = exception.response
        val status = response.status
        val error = response.body<ResponseFailure>()
        return when (status) {
            TooManyRequests -> RateLimitException(status, error, exception)

            BadRequest,
            NotFound,
            Conflict,
            UnsupportedMediaType,
                -> InvalidRequestException(status, error, exception)

            Unauthorized -> AuthenticationException(status, error, exception)
            Forbidden -> PermissionException(status, error, exception)
            else -> UnknownAPIException(status, error, exception)
        }
    }
}
