package org.nirmato.ollama.client.http

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.cancel
import io.ktor.utils.io.core.Closeable
import io.ktor.utils.io.readUTF8Line
import org.nirmato.ollama.client.JsonLenient

/**
 * Perform HTTP requests.
 */
public interface HttpClientHandler : Closeable {
    /**
     * Perform an HTTP request.
     */
    public suspend fun <T : Any> handle(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T

    /**
     * Perform an HTTP request and transform the result.
     */
    public suspend fun <T : Any> handle(builder: HttpRequestBuilder.() -> Unit, block: suspend (response: HttpResponse) -> T)
}

/**
 * Perform an HTTP request.
 */
public suspend inline fun <reified T> HttpClientHandler.handle(noinline builder: HttpRequestBuilder.() -> Unit): T {
    return handle(typeInfo<T>(), builder)
}

/**
 * Perform an HTTP request and transform the result.
 */
internal inline fun <reified T : Any> HttpClientHandler.handleFlow(noinline builder: HttpRequestBuilder.() -> Unit): Flow<T> {
    return flow {
        handle(builder) { response ->
            streamEventsFrom(response)
        }
    }
}

/**
 * Get data as [Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#Event_stream_format).
 */
@Suppress("LoopWithTooManyJumpStatements")
internal suspend inline fun <reified T> FlowCollector<T>.streamEventsFrom(response: HttpResponse) {
    val channel = response.body<ByteReadChannel>()
    try {
        while (currentCoroutineContext().isActive && !channel.isClosedForRead) {
            val line = channel.readUTF8Line() ?: continue

            val value: T = JsonLenient.decodeFromString(line)

            emit(value)
        }
    } finally {
        channel.cancel()
    }
}
