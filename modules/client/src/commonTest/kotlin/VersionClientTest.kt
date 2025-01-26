package org.nirmato.ollama.client

import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers

internal class VersionClientTest {

    @Test
    fun version_validRequest_returnSuccess() = runTest {
        val mockHttpClientEngineFactory = MockHttpClientFactory(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{
                          "version": "1.2.3"
                        }""",
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.DEFAULT
            }
        }

        val ollamaClient = OllamaClient(mockHttpClientEngineFactory.createHttpClient())

        val response = ollamaClient.version.getVersion()

        println(response.toString())
    }
}
