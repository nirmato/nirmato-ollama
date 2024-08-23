package org.nirmato.ollama.client.internal

import org.nirmato.ollama.client.Logger
import io.ktor.client.plugins.logging.Logger as KtorLogger

/**
 * Convert [Logger] to a Ktor's Logger.
 */
internal fun Logger.toKtorLogger() = object : KtorLogger {
    override fun log(message: String) {
        this@toKtorLogger.log(message)
    }
}
