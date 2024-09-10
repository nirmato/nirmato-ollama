package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow
import org.nirmato.ollama.api.ChatCompletionRequest.*

public interface ChatApi {
    /**
     * Generate the next message in a chat with a provided model.
     * This is a streaming endpoint, so there will be a series of responses.
     * The final response object will include statistics and additional data from the request.
     */
    public suspend fun chatCompletion(chatCompletionRequest: ChatCompletionRequest): ChatCompletionResponse

    /**
     * Generate the next message in a chat with a provided model.
     * This is a streaming endpoint, so there will be a series of responses.
     * The final response object will include statistics and additional data from the request.
     */
    public fun chatCompletionFlow(chatCompletionRequest: ChatCompletionRequest): Flow<ChatCompletionResponse>
}

public suspend fun ChatApi.chatCompletion(block: ChatCompletionRequestBuilder.() -> Unit): ChatCompletionResponse {
    val chatCompletionRequest = ChatCompletionRequestBuilder().apply(block).build()
    return chatCompletion(chatCompletionRequest)
}

public fun ChatApi.chatCompletionFlow(block: ChatCompletionRequestBuilder.() -> Unit): Flow<ChatCompletionResponse> {
    val chatCompletionRequest = ChatCompletionRequestBuilder().apply(block).build()
    return chatCompletionFlow(chatCompletionRequest)
}
