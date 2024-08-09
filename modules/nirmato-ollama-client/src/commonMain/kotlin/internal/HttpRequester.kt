package org.nirmato.ollama.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import io.ktor.utils.io.core.Closeable

/**
 * Perform HTTP requests.
 */
internal interface HttpRequester : Closeable {

    /**
     * Perform an HTTP request.
     */
    suspend fun <T : Any> processRequest(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T

    /**
     * Perform an HTTP request and get a streamed result.
     */
    suspend fun <T : Any> processRequestFlow(builder: HttpRequestBuilder.() -> Unit, block: suspend (response: HttpResponse) -> T)
}

/**
 * Perform an HTTP request.
 */
internal suspend inline fun <reified T> HttpRequester.processRequest(noinline builder: HttpRequestBuilder.() -> Unit): T {
    return processRequest(typeInfo<T>(), builder)
}

/**
 * Perform an HTTP request and get a streamed result.
 */
internal inline fun <reified T : Any> HttpRequester.processRequestFlow(noinline builder: HttpRequestBuilder.() -> Unit): Flow<T> {
    return flow {
        processRequestFlow(builder) { response ->
            streamEventsFrom(response)
        }
    }
}
