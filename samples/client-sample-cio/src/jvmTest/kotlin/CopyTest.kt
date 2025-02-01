package org.nirmato.ollama.client.samples

import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.nirmato.ollama.api.CopyModelRequest.Companion.copyModelRequest
import org.nirmato.ollama.api.ShowModelRequest.Companion.showModelRequest
import org.nirmato.ollama.client.OllamaClient
import org.nirmato.ollama.client.ktor.KtorHttpClientFactory

class CopyTest {
    @Disabled
    @Test
    fun copyAndGetAndShow_validRequest_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(KtorHttpClientFactory(CIO) {
            defaultRequest {
                url("http://localhost:11434/api/")
            }
        }.createHttpClient())

        val newModel = "test-tinyllama:latest"

        val copyModelRequest = copyModelRequest {
            source("tinyllama")
            destination(newModel)
        }

        ollamaClient.models().copyModel(copyModelRequest)

        val model = ollamaClient.models().listModels().models?.first { it.name == newModel }

        val modelInfoRequest = showModelRequest {
            name(model?.name)
        }

        val modelInfo = ollamaClient.models().showModel(modelInfoRequest)

        assertEquals("llama", modelInfo.details?.family)
    }
}
