package org.nirmato.ollama.client

import kotlin.test.Test
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import io.ktor.http.headersOf
import io.ktor.utils.io.core.toByteArray
import org.nirmato.ollama.api.CopyModelRequest.Companion.copyModelRequest
import org.nirmato.ollama.api.CreateBlobRequest.Companion.createBlobRequest
import org.nirmato.ollama.api.CreateModelRequest.Companion.createModelRequest
import org.nirmato.ollama.api.DeleteModelRequest.Companion.deleteModelRequest
import org.nirmato.ollama.api.OctetByteArray
import org.nirmato.ollama.api.PullModelRequest.Companion.pullModelRequest
import org.nirmato.ollama.api.PushModelRequest.Companion.pushModelRequest
import org.nirmato.ollama.api.ShowModelInformationRequest.Companion.showModelInformationRequest

internal class ModelClientTest {

    @Test
    fun createModel_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{"status":"success"}""",
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }
        }

        val createModelRequest = createModelRequest {
            name = "mario"
            modelfile = "FROM tinyllama\nSYSTEM You are mario from Super Mario Bros."
        }
        val response = ollamaClient.createModel(createModelRequest)

        println(response.toString())
    }

    @Test
    fun checkBlob_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respondOk()
                }
            }
        }

        val createBlobRequest = createBlobRequest {
            digest = "sha256:d4dd5fe90054a4539584cd5f7e612a7121a3b8daa9b68a3aae929317251810b4"
            body = OctetByteArray("newblob".toByteArray())
        }

        ollamaClient.createBlob(createBlobRequest)
    }

    @Test
    fun listModels_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{
                      "models": [
                        {
                          "name": "codellama:13b",
                          "modified_at": "2023-11-04T14:56:49.277302595-07:00",
                          "size": 7365960935,
                          "digest": "9f438cb9cd581fc025612d27f7c1a6669ff83a8bb0ed86c94fcf4c5440555697",
                          "details": {
                            "format": "gguf",
                            "family": "llama",
                            "families": null,
                            "parameter_size": "13B",
                            "quantization_level": "Q4_0"
                          }
                        }
                      ]
                    }""",
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }
        }

        val response = ollamaClient.listModels()

        println(response.toString())
    }

    @Test
    fun showModelInfo_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{
                      "modelfile": "# Modelfile generated by \"ollama show\"\n",
                      "details": {
                        "parent_model": "",
                        "format": "gguf",
                        "family": "llama",
                        "families": [
                          "llama"
                        ],
                        "parameter_size": "8.0B",
                        "quantization_level": "Q4_0"
                      },
                      "model_info": {
                        "general.architecture": "llama",
                      }
                    }""",
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }
        }

        val modelInfoRequest = showModelInformationRequest {
            name = "mario"
        }

        val response = ollamaClient.showModelInformation(modelInfoRequest)

        println(response.toString())
    }


    @Test
    fun copyModel_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respondOk()
                }
            }
        }

        val copyModelRequest = copyModelRequest {
            source = "mario"
            destination = "mario2"
        }

        ollamaClient.copyModel(copyModelRequest)
    }

    @Test
    fun deleteModel_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respondOk()
                }
            }
        }

        val deleteModelRequest = deleteModelRequest {
            model = "mario2"
        }

        ollamaClient.deleteModel(deleteModelRequest)
    }

    @Test
    fun pullModel_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{
                      "status": "pulling manifest"
                    }""",
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }
        }

        val pullModelRequest = pullModelRequest {
            name = "tinyllama"
        }

        val response = ollamaClient.pullModel(pullModelRequest)

        println(response.toString())
    }

    @Test
    fun pushModel_validRequest_returnSuccess() = runTest(timeout = 1.minutes) {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        "{ \"status\": \"retrieving manifest\" }",
                        HttpStatusCode.OK,
                        headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    )
                }
            }
        }

        val pushModelRequest = pushModelRequest {
            model = "mario"
        }

        val response = ollamaClient.pushModel(pushModelRequest)

        println(response.toString())
    }
}