package org.nirmato.ollama.client.ktor

import kotlin.test.Test
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
import org.nirmato.ollama.api.ShowModelRequest.Companion.showModelRequest

internal class ModelClientTest {

    @Test
    fun createModel_validRequest_returnSuccess() = runTest {
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
            model("mario")
            from("tinyllama")
            system("You are mario from Super Mario Bros.")
        }
        val response = ollamaClient.createModel(createModelRequest)

        println(response.toString())
    }

    @Test
    fun createModelFlow_validRequest_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{"status":"using existing layer sha256:2af3b81862c6be03c769683af18efdadb2c33f60ff32ab6f83e42c043d6c7816"}
                            |{"status":"using existing layer sha256:af0ddbdaaa26f30d54d727f9dd944b76bdb926fdaf9a58f63f78c532f57c191f"}
                            |{"status":"using existing layer sha256:1741cf59ce26ff01ac614d31efc700e21e44dd96aed60a7c91ab3f47e440ef94"}
                            |{"status":"using existing layer sha256:fa956ab37b8c21152f975a7fcdd095c4fee8754674b21d9b44d710435697a00d"}
                            |{"status":"writing manifest"}
                            |{"status":"success"}
                        """.trimMargin(),
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }
        }

        val createModelRequest = createModelRequest {
            model("mario")
            from("tinyllama")
            system("You are mario from Super Mario Bros.")
        }
        val response = ollamaClient.createModelStream(createModelRequest)

        response.collect { println(it) }
    }

    @Test
    fun checkBlob_validRequest_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respondOk()
                }
            }
        }

        val createBlobRequest = createBlobRequest {
            digest("sha256:d4dd5fe90054a4539584cd5f7e612a7121a3b8daa9b68a3aae929317251810b4")
            body(OctetByteArray("newblob".toByteArray()))
        }

        ollamaClient.createBlob(createBlobRequest)
    }

    @Test
    fun listModels_validRequest_returnSuccess() = runTest {
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
                                        "parent_model": "",
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
    fun listRunningModels_validRequest_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{
                            "models": [
                                {
                                    "name": "mistral:latest",
                                    "model": "mistral:latest",
                                    "size": 5137025024,
                                    "digest": "2ae6f6dd7a3dd734790bbbf58b8909a606e0e7e97e94b7604e0aa7ae4490e6d8",
                                    "details": {
                                        "parent_model": "",
                                        "format": "gguf",
                                        "family": "llama",
                                        "families": [
                                            "llama"
                                        ],
                                        "parameter_size": "7.2B",
                                        "quantization_level": "Q4_0"
                                    },
                                    "expires_at": "2024-06-04T14:38:31.83753-07:00",
                                    "size_vram": 5137025024
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
    fun showModelInfo_validRequest_returnSuccess() = runTest {
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
                                "general.file_type": 2,
                                "general.parameter_count": 8030261248,
                                "general.quantization_version": 2,
                                "llama.attention.head_count": 32,
                                "llama.attention.head_count_kv": 8,
                                "llama.attention.layer_norm_rms_epsilon": 0.00001,
                                "llama.block_count": 32,
                                "llama.context_length": 8192,
                                "llama.embedding_length": 4096,
                                "llama.feed_forward_length": 14336,
                                "llama.rope.dimension_count": 128,
                                "llama.rope.freq_base": 500000,
                                "llama.vocab_size": 128256,
                                "tokenizer.ggml.bos_token_id": 128000,
                                "tokenizer.ggml.eos_token_id": 128009,
                                "tokenizer.ggml.merges": [],
                                "tokenizer.ggml.model": "gpt2",
                                "tokenizer.ggml.pre": "llama-bpe",
                                "tokenizer.ggml.token_type": [],
                                "tokenizer.ggml.tokens": []
                            },
                            "capabilities": [
                                "completion",
                                "vision"
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

        val modelInfoRequest = showModelRequest {
            name("mario")
        }

        val response = ollamaClient.showModel(modelInfoRequest)

        println(response.toString())
    }


    @Test
    fun copyModel_validRequest_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respondOk()
                }
            }
        }

        val copyModelRequest = copyModelRequest {
            source("mario")
            destination("mario2")
        }

        ollamaClient.copyModel(copyModelRequest)
    }

    @Test
    fun deleteModel_validRequest_returnSuccess() = runTest {
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
    fun pullModel_validRequest_returnSuccess() = runTest {
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
            name("tinyllama")
        }

        val response = ollamaClient.pullModel(pullModelRequest)

        println(response.toString())
    }

    @Test
    fun pushModel_validRequest_returnSuccess() = runTest {
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
            model("mario")
        }

        val response = ollamaClient.pushModel(pushModelRequest)

        println(response.toString())
    }
}
