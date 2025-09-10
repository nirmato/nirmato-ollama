package org.nirmato.ollama.client.ktor

import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import org.nirmato.ollama.api.ChatRequest.Companion.chatRequest
import org.nirmato.ollama.api.Format.FormatSchema
import org.nirmato.ollama.api.Format.FormatType
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Options
import org.nirmato.ollama.api.Role
import org.nirmato.ollama.api.Role.*
import org.nirmato.ollama.api.Tool
import org.nirmato.ollama.api.Tool.ToolFunction
import org.nirmato.ollama.api.Tool.ToolParameters
import org.nirmato.ollama.api.ToolCall
import org.nirmato.ollama.api.ToolCall.FunctionCall

internal class ChatClientTest {

    @Test
    fun chat_withNoStreaming_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                            "model": "tinyllama",
                            "created_at": "2025-01-05T01:33:06.981491109Z",
                            "message": {
                                "role": "assistant",
                                "content": "The sky blue color is not an inherent characteristic of the sun but rather a result of the atmosphere and the environment above it."
                            },
                            "done_reason": "stop",
                            "done": true,
                            "total_duration": 3375883588,
                            "load_duration": 1025258177,
                            "prompt_eval_count": 40,
                            "prompt_eval_duration": 263000000,
                            "eval_count": 142,
                            "eval_duration": 2029000000
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

        val chatRequest = chatRequest {
            model("mock-model")
            messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
        }
        val response = ollamaClient.chat(chatRequest)

        println(response.toString())
    }

    @Test
    fun chat_withNoStreamingWithTools_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                            "model": "tinyllama",
                            "created_at": "2025-01-05T01:33:06.981491109Z",
                            "message": {
                                "role": "assistant",
                                "content": "The sky blue color is not an inherent characteristic of the sun but rather a result of the atmosphere and the environment above it."
                            },
                            "tool_calls": [{
                              "function": {
                                "name": "get_weather",
                                  "arguments": {
                                    "city": "Tokyo"
                                  }
                              }
                            }],
                            "done_reason": "stop",
                            "done": true,
                            "total_duration": 3375883588,
                            "load_duration": 1025258177,
                            "prompt_eval_count": 40,
                            "prompt_eval_duration": 263000000,
                            "eval_count": 142,
                            "eval_duration": 2029000000
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

        val chatRequest = chatRequest {
            model("mock-model")
            messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
            tools(
                listOf(
                    Tool(
                        type = "function",
                        function = ToolFunction(
                            name = "get_weather",
                            description = "Get the current weather for a city",
                            parameters = ToolParameters(
                                required = listOf("city"),
                                properties = mapOf(
                                    "city" to buildJsonObject {
                                        put("type", "string")
                                        put("description", "The city to get the weather for, e.g. San Francisco, CA")
                                    }
                                )
                            )
                        )
                    )
                ))
        }
        val response = ollamaClient.chat(chatRequest)

        println(response.toString())
    }

    @Test
    fun chat_withNoStreamingWithToolsWithHistory_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                              "model": "llama3.2",
                              "created_at": "2025-07-07T20:43:37.688511Z",
                              "message": {
                                "role": "assistant",
                                "content": "The current temperature in Toronto is 11°C."
                              },
                              "done_reason": "stop",
                              "done": true,
                              "total_duration": 890771750,
                              "load_duration": 707634750,
                              "prompt_eval_count": 94,
                              "prompt_eval_duration": 91703208,
                              "eval_count": 11,
                              "eval_duration": 90282125
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

        val chatRequest = chatRequest {
            model("mock-model")
            messages(
                listOf(
                    Message(role = USER, content = "What is the weather in Toronto?"),
                    Message(
                        role = ASSISTANT,
                        content = "",
                        tools = listOf(
                            ToolCall(
                                function = FunctionCall(
                                    name = "get_temperature",
                                    arguments = mapOf("city" to JsonPrimitive("Toronto"))
                                )
                            )
                        )
                    ),
                    Message(role = TOOL, content = "11 degrees celsius", toolName = "get_temperature")
                )
            )
            tools(
                listOf(
                    Tool(
                        type = "function",
                        function = ToolFunction(
                            name = "get_weather",
                            description = "Get the current weather for a city",
                            parameters = ToolParameters(
                                required = listOf("city"),
                                properties = mapOf(
                                    "city" to buildJsonObject {
                                        put("type", "string")
                                        put("description", "The city to get the weather for, e.g. San Francisco, CA")
                                    }
                                )
                            )
                        )
                    )
                ))
        }
        val response = ollamaClient.chat(chatRequest)

        println(response.toString())
    }

    @Test
    fun chat_withStreaming_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{ "model": "tinyllama", "created_at": "2025-01-05T01:33:06.981491109Z", "message": { "role": "assistant", "content": "The sky blue color is not an inherent characteristic of the sun but rather a result of the atmosphere and the environment above it." }, "done_reason": "stop", "done": true, "total_duration": 3375883588, "load_duration": 1025258177, "prompt_eval_count": 40, "prompt_eval_duration": 263000000, "eval_count": 142, "eval_duration": 2029000000 }
                        { "model": "tinyllama", "created_at": "2025-01-05T01:33:06.981491109Z", "message": { "role": "assistant", "content": "The sky blue color is not an inherent characteristic of the sun but rather a result of the atmosphere and the environment above it." }, "done_reason": "stop", "done": true, "total_duration": 3375883588, "load_duration": 1025258177, "prompt_eval_count": 40, "prompt_eval_duration": 263000000, "eval_count": 142, "eval_duration": 2029000000 }""",
                            status = HttpStatusCode.OK,
                            headers {
                                append(HttpHeaders.ContentType, "application/json")
                            }
                        )
                    }
                }
            }
        }

        val chatRequest = chatRequest {
            model("mock-model")
            messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
        }
        val response = ollamaClient.chatStream(chatRequest)

        response.collect { println(it) }
    }

    @Test
    fun chat_withStreamingWithTools_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """
                                { "model": "llama3.2", "created_at": "2025-07-07T20:22:19.184789Z", "message": { "role": "assistant", "content": "", "tool_calls": [ { "function": { "name": "get_weather", "arguments": { "city": "Tokyo" } } } ] }, "done": false }
                                { "model": "llama3.2", "created_at": "2025-07-07T20:22:20.184789Z", "message": { "role": "assistant", "content": "", "tool_calls": [ { "function": { "name": "get_weather", "arguments": { "city": "Tokyo" } } } ] }, "done": true }""",
                            status = HttpStatusCode.OK,
                            headers {
                                append(HttpHeaders.ContentType, "application/json")
                            }
                        )
                    }
                }
            }
        }

        val chatRequest = chatRequest {
            model("mock-model")
            messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
            tools(
                listOf(
                    Tool(
                        type = "function",
                        function = ToolFunction(
                            name = "get_weather",
                            description = "Get the current weather for a city",
                            parameters = ToolParameters(
                                required = listOf("city"),
                                properties = mapOf(
                                    "city" to buildJsonObject {
                                        put("type", "string")
                                        put("description", "The city to get the weather for, e.g. San Francisco, CA")
                                    }
                                )
                            )
                        )
                    )
                ))
        }

        val response = ollamaClient.chatStream(chatRequest)

        response.collect { println(it) }
    }

    @Test
    fun chat_withImages_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                            "model": "llava",
                            "created_at": "2025-01-05T13:08:56.341574689Z",
                            "message": {
                                "role": "assistant",
                                "content": " The image shows a cartoon of a character that resembles an animal with one arm raised as if waving or saying hello. It's styled with simple lines and appears to be a creative or humorous drawing rather than a realistic depiction. There's also a speech bubble coming from the character, but the content of what is being said isn't visible in the image provided. "
                            },
                            "done_reason": "stop",
                            "done": true,
                            "total_duration": 7089868214,
                            "load_duration": 4837857,
                            "prompt_eval_count": 591,
                            "prompt_eval_duration": 85000000,
                            "eval_count": 83,
                            "eval_duration": 6998000000
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

        val request = chatRequest {
            model("llava")
            messages(
                listOf(
                    Message(
                        role = USER,
                        content = "what is in this image?",
                        images = listOf(
                            "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"
                        )
                    ),
                )
            )
        }

        val response = ollamaClient.chat(request)

        println(response.toString())
    }

    @Test
    fun chat_withJsonStructuredOutput_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                            "model": "llama3.2",
                            "created_at": "2025-01-05T01:39:34.325843522Z",
                            "message": {
                                "role": "assistant",
                                "content": "{ \"age\": 22, \"available\": false }"
                            },
                            "done_reason": "stop",
                            "done": true,
                            "total_duration": 3173503119,
                            "load_duration": 1793329255,
                            "prompt_eval_count": 49,
                            "prompt_eval_duration": 886000000,
                            "eval_count": 13,
                            "eval_duration": 486000000
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

        val request = chatRequest {
            model("mock-model")
            messages(listOf(Message(role = USER, content = "Ollama is 22 years old and busy saving the world. Return a JSON object with the age and availability.")))
            format(FormatType.JSON)
            options(
                Options(
                    temperature = 0f,
                )
            )
        }

        val response = ollamaClient.chat(request)

        println(response.toString())
    }

    @Test
    fun chat_withSchemaStructuredOutput_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                            "model": "llama3.2",
                            "created_at": "2025-01-05T01:39:34.325843522Z",
                            "message": {
                                "role": "assistant",
                                "content": "{ \"age\": 22, \"available\": false }"
                            },
                            "done_reason": "stop",
                            "done": true,
                            "total_duration": 3173503119,
                            "load_duration": 1793329255,
                            "prompt_eval_count": 49,
                            "prompt_eval_duration": 886000000,
                            "eval_count": 13,
                            "eval_duration": 486000000
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

        val request = chatRequest {
            model("mock-model")
            messages(listOf(Message(role = USER, content = "Ollama is 22 years old and busy saving the world. Return a JSON object with the age and availability.")))
            format(
                FormatSchema(
                    JsonObject(
                        mapOf(
                            "type" to JsonPrimitive("object"),
                            "properties" to JsonObject(
                                mapOf(
                                    "age" to JsonObject(mapOf("type" to JsonPrimitive("integer"))),
                                    "available" to JsonObject(mapOf("type" to JsonPrimitive("boolean")))
                                )
                            ),
                            "required" to JsonArray(listOf(JsonPrimitive("age"), JsonPrimitive("available")))
                        )
                    )
                )
            )
            options(
                Options(
                    temperature = 0f,
                )
            )
        }

        val response = ollamaClient.chat(request)

        println(response.toString())
    }

    @Test
    fun chat_withTools_returnSuccess() = runTest {
        val ollamaClient = OllamaClient(MockHttpClientEngineFactory()) {
            httpClient {
                engine {
                    addHandler {
                        respond(
                            content = """{
                            "model": "llama3.2",
                            "created_at": "2025-01-05T12:59:55.141725984Z",
                            "message": {
                                "role": "assistant",
                                "content": "",
                                "tool_calls": [{
                                        "function": {
                                            "name": "get_current_weather",
                                            "arguments": {
                                                "format": "celsius",
                                                "location": "Paris"
                                            }
                                        }
                                    }
                                ]
                            },
                            "done_reason": "stop",
                            "done": true,
                            "total_duration": 7227102596,
                            "load_duration": 2559705615,
                            "prompt_eval_count": 217,
                            "prompt_eval_duration": 3714000000,
                            "eval_count": 25,
                            "eval_duration": 951000000
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

        val request = chatRequest {
            model("llama3.2")
            messages(
                listOf(
                    Message(role = USER, content = "What is the weather today in Paris?"),
                )
            )
            tools(
                listOf(
                    Tool(
                        type = "function",
                        function = ToolFunction(
                            name = "get_current_weather",
                            description = "Get the current weather for a location",
                            parameters = ToolParameters(
                                required = listOf("location", "format"),
                                properties = mapOf(
                                    "location" to buildJsonObject {
                                        put("type", "string")
                                        put("description", "The location to get the weather for, e.g. San Francisco, CA")
                                    },
                                    "format" to buildJsonObject {
                                        put("type", "string")
                                        put("description", "The format to return the weather in, e.g. 'celsius' or 'fahrenheit'")
                                        put("enumValues", buildJsonArray {
                                            add(JsonPrimitive("celsius"))
                                            add(JsonPrimitive("fahrenheit"))
                                        })
                                    }
                                )
                            )
                        )
                    )
                )
            )
        }

        val response = ollamaClient.chat(request)

        println(response.toString())
    }
}
