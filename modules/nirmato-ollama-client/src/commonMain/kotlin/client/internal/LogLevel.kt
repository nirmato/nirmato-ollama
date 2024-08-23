package org.nirmato.ollama.client.internal

import org.nirmato.ollama.client.LogLevel
import io.ktor.client.plugins.logging.LogLevel as KtorLogLevel

/**
 * Convert [LogLevel] to a Ktor's LogLevel.
 */
internal fun LogLevel.toKtorLogLevel() = when (this) {
    LogLevel.All -> KtorLogLevel.ALL
    LogLevel.Headers -> KtorLogLevel.HEADERS
    LogLevel.Body -> KtorLogLevel.BODY
    LogLevel.Info -> KtorLogLevel.INFO
    LogLevel.None -> KtorLogLevel.NONE
}
