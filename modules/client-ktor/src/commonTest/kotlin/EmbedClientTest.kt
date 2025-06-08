package org.nirmato.ollama.client.ktor

import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import org.nirmato.ollama.api.EmbedRequest.Companion.embedRequest
import org.nirmato.ollama.api.EmbeddedInput.EmbeddedList
import org.nirmato.ollama.api.EmbeddedInput.EmbeddedText

internal class EmbedClientTest {

    @Test
    fun generateEmbed_validRequest_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
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
            }
        }

        val generateEmbedRequest = embedRequest {
            model("tinyllama")
            input(EmbeddedList(listOf(EmbeddedText("Why is the sky blue?"), EmbeddedText("Why is the grass green?"))))
        }

        val response = ollamaClient.generateEmbed(generateEmbedRequest)

        println(response.toString())
    }
}
