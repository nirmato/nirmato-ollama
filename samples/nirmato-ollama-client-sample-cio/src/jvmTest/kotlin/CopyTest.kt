package org.nirmato.ollama.client.samples

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.cio.CIO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.nirmato.ollama.api.CopyModelRequest.Companion.copyModelRequest
import org.nirmato.ollama.api.ShowModelInformationRequest.Companion.showModelInformationRequest
import org.nirmato.ollama.client.LogLevel
import org.nirmato.ollama.client.LoggingConfig
import org.nirmato.ollama.client.OllamaHost
import org.nirmato.ollama.client.RetryStrategy
import org.nirmato.ollama.client.TimeoutConfig
import org.nirmato.ollama.client.OllamaClient

class CopyTest {
    @Disabled
    @Test
    fun copyAndGetAndShow_validRequest_returnSuccess() = runTest {
        val ollama = OllamaClient {
            logging = LoggingConfig(logLevel = LogLevel.All)
            timeout = TimeoutConfig(socket = 30.seconds)
            host = OllamaHost.Local
            retry = RetryStrategy(0)
            engine = CIO.create()
        }

        val newModel = "test-tinyllama:latest"

        val copyModelRequest = copyModelRequest {
            source = "tinyllama"
            destination = newModel
        }

        ollama.copyModel(copyModelRequest)

        val model = ollama.listModels().models?.first { it.name == newModel }

        val modelInfoRequest = showModelInformationRequest {
            name = model?.name
        }

        val modelInfo = ollama.showModelInformation(modelInfoRequest)

        assertEquals("llama", modelInfo.details?.family)
    }
}
