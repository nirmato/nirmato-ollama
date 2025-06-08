package org.nirmato.ollama.client.ktor

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

internal class MonitoringClientTest {

    @Test
    fun monitoring_validRequest_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                          "status": "500"
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
        }

        val response = ollamaClient.getMonitoring()

        println(response.toString())
    }
}
