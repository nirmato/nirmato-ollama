package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow
import org.nirmato.ollama.api.CompletionRequest.CompletionRequestBuilder

public interface CompletionsApi {
    /**
     * Generate a response for a given prompt with a provided model.
     * This is a streaming endpoint, so there will be a series of responses. The final response object will include statistics and additional data from the request.
     */
    public suspend fun completion(completionRequest: CompletionRequest): CompletionResponse

    /**
     * Generate a response for a given prompt with a provided model.
     * This is a streaming endpoint, so there will be a series of responses. The final response object will include statistics and additional data from the request.
     */
    public fun completionFlow(completionRequest: CompletionRequest): Flow<CompletionResponse>
}

public suspend fun CompletionsApi.completion(block: CompletionRequestBuilder.() -> Unit): CompletionResponse {
    val completionRequest = CompletionRequestBuilder().apply(block).build()
    return completion(completionRequest)
}

public fun CompletionsApi.completionFlow(block: CompletionRequestBuilder.() -> Unit): Flow<CompletionResponse> {
    val completionRequest = CompletionRequestBuilder().apply(block).build()
    return completionFlow(completionRequest)
}
