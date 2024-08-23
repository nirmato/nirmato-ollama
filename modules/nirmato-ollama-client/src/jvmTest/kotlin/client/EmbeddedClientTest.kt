package org.nirmato.ollama.client

import kotlin.test.Test
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import org.nirmato.ollama.api.EmbeddingRequest.Companion.embeddingRequest

internal class EmbeddedClientTest {

    @Test
    fun generateEmbedding_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val mockEngine = MockEngine.create {
            addHandler {
                respond(
                    content = """{
                      "model": "all-minilm",
                      "embeddings": [[
                        0.010071029, -0.0017594862, 0.05007221, 0.04692972, 0.054916814,
                        0.008599704, 0.105441414, -0.025878139, 0.12958129, 0.031952348
                      ],[
                        -0.0098027075, 0.06042469, 0.025257962, -0.006364387, 0.07272725,
                        0.017194884, 0.09032035, -0.051705178, 0.09951512, 0.09072481
                      ]]
                    }""",
                    status = HttpStatusCode.OK,
                    headers {
                        append(HttpHeaders.ContentType, "application/json")
                    }
                )
            }
        }

        val ollama = createOllamaClient {
            logging = LoggingConfig(logLevel = LogLevel.All)
            timeout = TimeoutConfig(socket = 30.seconds)
            host = OllamaHost.Local
            retry = RetryStrategy(0)
            engine = mockEngine
        }

        val generateEmbeddingRequest = embeddingRequest {
            model = "tinyllama"
            input = listOf("Why is the sky blue?")
        }

        val response = ollama.generateEmbedding(generateEmbeddingRequest)

        println(response.toString())
    }
}
