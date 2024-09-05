package org.nirmato.ollama.client.internal

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
internal interface RequestHandler : Closeable {

    /**
     * Perform an HTTP request.
     */
    suspend fun <T : Any> handle(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T

    /**
     * Perform an HTTP request and get a streamed result.
     */
    suspend fun <T : Any> handleFlow(builder: HttpRequestBuilder.() -> Unit, block: suspend (response: HttpResponse) -> T)
}

/**
 * Perform an HTTP request.
 */
internal suspend inline fun <reified T> RequestHandler.handle(noinline builder: HttpRequestBuilder.() -> Unit): T {
    return handle(typeInfo<T>(), builder)
}

/**
 * Perform an HTTP request and get a streamed result.
 */
internal inline fun <reified T : Any> RequestHandler.handleFlow(noinline builder: HttpRequestBuilder.() -> Unit): Flow<T> {
    return flow {
        handleFlow(builder) { response ->
            streamEventsFrom(response)
        }
    }
}
