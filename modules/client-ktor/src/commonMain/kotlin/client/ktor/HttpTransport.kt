package org.nirmato.ollama.client.ktor

import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
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
import io.ktor.util.reflect.typeInfo
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.CancellationException
import io.ktor.utils.io.cancel
import io.ktor.utils.io.readUTF8Line
import org.nirmato.ollama.api.AuthenticationException
import org.nirmato.ollama.api.FailureResponse
import org.nirmato.ollama.api.InvalidRequestException
import org.nirmato.ollama.api.OllamaClientException
import org.nirmato.ollama.api.OllamaException
import org.nirmato.ollama.api.OllamaGenericIOException
import org.nirmato.ollama.api.OllamaServerException
import org.nirmato.ollama.api.OllamaTimeoutException
import org.nirmato.ollama.api.PermissionException
import org.nirmato.ollama.api.RateLimitException
import org.nirmato.ollama.api.UnknownAPIException

public const val MAX_READ_LINE: Int = 128_000

/**
 * A transport layer for handling HTTP requests and responses in the Ollama client.
 * This class provides methods to perform requests, handle exceptions, and stream events from responses.
 *
 * @property httpClient The HTTP client used to make requests.
 * @property clientConfig Configuration settings for the Ollama client.
 */
internal class HttpTransport(
    private val httpClient: HttpClient,
    private val clientConfig: OllamaClientConfig,
) {
    @Suppress("TooGenericExceptionCaught")
    suspend fun <T : Any> handleRequest(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T = try {
        val response = httpClient.request(builder)

        when (response.status) {
            OK -> response.body<T>(info)
            else -> throw handleClientException(ClientRequestException(response, ""))
        }

    } catch (e: Exception) {
        throw handleException(e)
    }

    /**
     * Perform an HTTP request.
     */
    suspend inline fun <reified T> handleRequest(noinline builder: HttpRequestBuilder.() -> Unit): T {
        return handleRequest(typeInfo<T>(), builder)
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T : Any> handleRequest(builder: HttpRequestBuilder.() -> Unit, block: suspend (response: HttpResponse) -> T) {
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

    /**
     * Perform an HTTP request and transform the result.
     */
    inline fun <reified T : Any> handleFlow(noinline builder: HttpRequestBuilder.() -> Unit): Flow<T> {
        return cancellableFlow {
            handleRequest(builder) { response ->
                streamEventsFrom(response)
            }
        }
    }

    @Suppress("LoopWithTooManyJumpStatements")
    suspend inline fun <reified T> FlowCollector<T>.streamEventsFrom(response: HttpResponse) {
        val channel = response.body<ByteReadChannel>()
        try {
            while (currentCoroutineContext().isActive && !channel.isClosedForRead) {
                val line = channel.readUTF8Line(MAX_READ_LINE)?.takeUnless { it.isEmpty() } ?: continue

                val value: T = this@HttpTransport.clientConfig.jsonConfig.decodeFromString(line)

                emit(value)
            }
        } finally {
            channel.cancel()
        }
    }

    /**
     * Creates a cancellable flow from a regular flow.
     * This is useful for streaming responses that need to be cancelled when no longer needed.
     *
     * @param T The type of data in the flow
     * @param block The suspend lambda that produces values for the flow
     * @return A new Flow that can be cancelled
     */
    fun <T> cancellableFlow(block: suspend FlowCollector<T>.() -> Unit): Flow<T> = flow {
        try {
            block()
        } catch (e: Exception) {
            if (e is CancellationException) {
                coroutineContext.cancel()
            } else {
                throw e
            }
        }
    }

    /**
     * Handles various exceptions that can occur during an API request and converts them into appropriate [OllamaException] instances.
     */
    private suspend fun handleException(cause: Throwable): RuntimeException = when (cause) {
        is CancellationException -> cause
        is ClientRequestException -> handleClientException(cause)
        is ServerResponseException -> OllamaServerException(cause)

        is HttpRequestTimeoutException,
        is SocketTimeoutException,
        is ConnectTimeoutException,
            -> OllamaTimeoutException(cause)

        is IOException -> OllamaGenericIOException(cause)
        else -> OllamaClientException(cause)
    }

    /**
     * Converts a [ClientRequestException] into a corresponding [OllamaException] based on the HTTP status code.
     * This function helps in handling specific API errors and categorizing them into appropriate exception classes.
     */
    private suspend fun handleClientException(exception: ClientRequestException): OllamaException {
        val response = exception.response
        val status = response.status
        val error = response.body<FailureResponse>()

        return when (status) {
            TooManyRequests -> RateLimitException(status.value, error, exception)

            BadRequest,
            NotFound,
            Conflict,
            UnsupportedMediaType,
                -> InvalidRequestException(status.value, error, exception)

            Unauthorized -> AuthenticationException(status.value, error, exception)
            Forbidden -> PermissionException(status.value, error, exception)
            else -> UnknownAPIException(status.value, error, exception)
        }
    }
}
