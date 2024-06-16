package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow

public interface CompletionsApi {
    /**
     * Generate a response for a given prompt with a provided model.
     * This is a streaming endpoint, so there will be a series of responses. The final response object will include statistics and additional data from the request.
     */
    public suspend fun generateCompletion(generateCompletionRequest: GenerateCompletionRequest): GenerateCompletionResponse

    /**
     * Generate a response for a given prompt with a provided model.
     * This is a streaming endpoint, so there will be a series of responses. The final response object will include statistics and additional data from the request.
     */
    public fun generateCompletions(generateCompletionRequest: GenerateCompletionRequest): Flow<GenerateCompletionResponse>
}
