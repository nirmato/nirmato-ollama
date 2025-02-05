package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow

public interface CompletionsApi {
    /**
     * Generate a response for a given prompt with a provided model.
     * This is a streaming endpoint, so there will be a series of responses. The final response object will include statistics and additional data from the request.
     */
    public suspend fun completion(completionRequest: CompletionRequest): CompletionResponse

    /**
     * @see #completion
     */
    public fun completionStream(completionRequest: CompletionRequest): Flow<CompletionResponse>
}
