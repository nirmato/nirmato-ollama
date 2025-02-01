package org.nirmato.ollama.client

import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import org.nirmato.ollama.api.CompletionRequest.Companion.completionRequest

internal class CompletionsClientTest {

    @Test
    fun completion_validRequest_returnSuccess() = runTest {
        val mockHttpClientEngineFactory = MockHttpClientFactory(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{
                            "model": "tinyllama",
                            "created_at": "2025-01-05T21:48:31.086207438Z",
                            "response": "The sky blue color occurs due to the presence of tiny organic molecules called chlorophyll. These molecules absorb blue light, which has wavelengths between 400 nm (near-infrared) and 725 nm (ultraviolet), emitted by plants during daylight hours. The excess blue light is then reflected back into the earth's atmosphere, causing the sky to turn a vibrant blue color.\n\nThe exact chemical composition of chlorophyll is still not fully understood, but it is thought that there are several types of chlorophyll present in the leaves of plants, each with different absorption properties. The blue light absorbing property of these chlorophyll molecules is responsible for the vibrant and distinctive color of the sky during daylight hours.",
                            "done": true,
                            "done_reason": "stop",
                            "context": [529, 29989, 5205, 29989, 29958, 13, 3492, 526, 263, 8444, 319, 29902, 20255, 29889, 2, 29871, 13, 29966, 29989, 1792, 29989, 29958, 13, 11008, 338, 278, 14744, 7254, 29973, 2, 29871, 13, 29966, 29989, 465, 22137, 29989, 29958, 13, 1576, 14744, 7254, 2927, 10008, 2861, 304, 278, 10122, 310, 21577, 2894, 293, 13206, 21337, 2000, 521, 5095, 3021, 15114, 29889, 4525, 13206, 21337, 17977, 29890, 7254, 3578, 29892, 607, 756, 10742, 2435, 4141, 9499, 1546, 29871, 29946, 29900, 29900, 302, 29885, 313, 28502, 29899, 7192, 25983, 29881, 29897, 322, 29871, 29955, 29906, 29945, 302, 29885, 313, 499, 5705, 601, 1026, 511, 20076, 9446, 491, 18577, 2645, 2462, 4366, 6199, 29889, 450, 19163, 7254, 3578, 338, 769, 25312, 1250, 964, 278, 8437, 29915, 29879, 25005, 29892, 10805, 278, 14744, 304, 2507, 263, 3516, 2634, 593, 7254, 2927, 29889, 13, 13, 1576, 2684, 22233, 15259, 310, 521, 5095, 3021, 15114, 338, 1603, 451, 8072, 23400, 517, 397, 29892, 541, 372, 338, 2714, 393, 727, 526, 3196, 4072, 310, 521, 5095, 3021, 15114, 2198, 297, 278, 11308, 310, 18577, 29892, 1269, 411, 1422, 17977, 683, 4426, 29889, 450, 7254, 3578, 17977, 10549, 2875, 310, 1438, 521, 5095, 3021, 15114, 13206, 21337, 338, 14040, 363, 278, 3516, 2634, 593, 322, 8359, 573, 2927, 310, 278, 14744, 2645, 2462, 4366, 6199, 29889],
                            "total_duration": 3824372967,
                            "load_duration": 1023083163,
                            "prompt_eval_count": 40,
                            "prompt_eval_duration": 251000000,
                            "eval_count": 177,
                            "eval_duration": 2542000000
                        }""",
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }
        }

        val ollamaClient = OllamaClient(mockHttpClientEngineFactory.createHttpClient())

        val completionRequest = completionRequest {
            model("tinyllama")
            prompt("Why is the sky blue?")
        }
        val response = ollamaClient.completions().completion(completionRequest)

        println(response.toString())
    }

    @Test
    fun generateCompletion_validStreamRequest_returnSuccess() = runTest {
        val mockHttpClientEngineFactory = MockHttpClientFactory(MockHttpClientEngineFactory()) {
            engine {
                addHandler {
                    respond(
                        content = """{"model":"tinyllama","created_at":"2025-01-05T21:48:31.086207438Z","response":"The sky blue color occurs due to the presence of tiny organic molecules called chlorophyll. These molecules absorb blue light, which has wavelengths between 400 nm (near-infrared) and 725 nm (ultraviolet), emitted by plants during daylight hours. The excess blue light is then reflected back into the earth's atmosphere, causing the sky to turn a vibrant blue color.\n\nThe exact chemical composition of chlorophyll is still not fully understood, but it is thought that there are several types of chlorophyll present in the leaves of plants, each with different absorption properties. The blue light absorbing property of these chlorophyll molecules is responsible for the vibrant and distinctive color of the sky during daylight hours.","done":true,"done_reason":"stop","context":[529,29989,5205,29989,29958,13,3492,526,263,8444,319,29902,20255,29889,2,29871,13,29966,29989,1792,29989,29958,13,11008,338,278,14744,7254,29973,2,29871,13,29966,29989,465,22137,29989,29958,13,1576,14744,7254,2927,10008,2861,304,278,10122,310,21577,2894,293,13206,21337,2000,521,5095,3021,15114,29889,4525,13206,21337,17977,29890,7254,3578,29892,607,756,10742,2435,4141,9499,1546,29871,29946,29900,29900,302,29885,313,28502,29899,7192,25983,29881,29897,322,29871,29955,29906,29945,302,29885,313,499,5705,601,1026,511,20076,9446,491,18577,2645,2462,4366,6199,29889,450,19163,7254,3578,338,769,25312,1250,964,278,8437,29915,29879,25005,29892,10805,278,14744,304,2507,263,3516,2634,593,7254,2927,29889,13,13,1576,2684,22233,15259,310,521,5095,3021,15114,338,1603,451,8072,23400,517,397,29892,541,372,338,2714,393,727,526,3196,4072,310,521,5095,3021,15114,2198,297,278,11308,310,18577,29892,1269,411,1422,17977,683,4426,29889,450,7254,3578,17977,10549,2875,310,1438,521,5095,3021,15114,13206,21337,338,14040,363,278,3516,2634,593,322,8359,573,2927,310,278,14744,2645,2462,4366,6199,29889],"total_duration":3824372967,"load_duration":1023083163,"prompt_eval_count":40,"prompt_eval_duration":251000000,"eval_count":177,"eval_duration":2542000000}""",
                        status = HttpStatusCode.OK,
                        headers {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                }
            }
        }

        val ollamaClient = OllamaClient(mockHttpClientEngineFactory.createHttpClient())

        val completionRequest = completionRequest {
            model("tinyllama")
            prompt("Why is the sky blue?")
        }

        val response = ollamaClient.completions().completionFlow(completionRequest)

        response.collect { println(it) }
    }
}
