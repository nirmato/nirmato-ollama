package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow

public interface ChatApi {

    /**
     * Generate the next message in a chat with a provided model.
     * This is a streaming endpoint, so there will be a series of responses.
     * The final response object will include statistics and additional data from the request.
     */
    public suspend fun generateChatCompletion(generateChatCompletionRequest: GenerateChatCompletionRequest): GenerateChatCompletionResponse

    /**
     * Generate the next message in a chat with a provided model.
     * This is a streaming endpoint, so there will be a series of responses.
     * The final response object will include statistics and additional data from the request.
     */
    public fun generateChatCompletionFlow(generateChatCompletionRequest: GenerateChatCompletionRequest): Flow<GenerateChatCompletionResponse>
}
