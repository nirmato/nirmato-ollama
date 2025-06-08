package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow

public interface OllamaApi {

    /**
     * Sends a chat request.
     *
     * This is a function that gives you control over both the model's behavior and the conversation context.
     *
     * Example:
     * ```kotlin
     * val request = chatRequest {
     *   model("tinyllama")
     *   messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
     * }
     *
     * val response = ollamaClient.chat(request)
     * ```
     *
     * @param chatRequest The fully configured request object containing all parameters for the API call
     * @return A [ChatResponse] containing the model's response
     */
    public suspend fun chat(chatRequest: ChatRequest): ChatResponse

    /**
     * Streams chat responses chunk by chunk from the Ollama API.
     *
     * This is a function that gives you control over both the model's behavior and the conversation context.
     *
     * Example:
     * ```kotlin
     * val request = chatRequest {
     *   model("tinyllama")
     *   messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
     * }
     *
     * val response = ollamaClient.chatStream(request).collect { chunk ->
     *   print(chunk.response ?: "")
     * }
     * ```
     *
     * @param chatRequest The fully configured request object containing all parameters for the API call
     * @return A [Flow] of [ChatResponse] objects representing incremental updates
     */
    public fun chatStream(chatRequest: ChatRequest): Flow<ChatResponse>

    /**
     * Sends a chat completion request.
     *
     * This is a low-level function that handles the direct HTTP communication with the API.
     * Most users will prefer the higher-level [chat] functions instead.
     *
     * Example:
     * ```kotlin
     * val request = chatCompletionRequest {
     *   model("ollama-model")
     *   prompt("Why is the sky blue?")
     * }
     *
     * val response = ollamaClient.chatCompletion(request)
     * ```
     *
     * @param chatCompletionRequest The fully configured request object containing all parameters for the API call
     * @return A [ChatCompletionResponse] containing the model's response
     */
    public suspend fun chatCompletion(chatCompletionRequest: ChatCompletionRequest): ChatCompletionResponse

    /**
     * Streams chat completion responses chunk by chunk from the Ollama API.
     *
     * This function handles the low-level communication with the Server-Sent Events (SSE)
     * endpoint, allowing you to receive and process model responses in real-time as they're generated.
     *
     * Example:
     * ```kotlin
     * val request = chatCompletionRequest {
     *   model("ollama-model")
     *   prompt("Why is the sky blue?")
     * }
     *
     * val response = ollamaClient.chatCompletionStream(request).collect { chunk ->
     *   print(chunk.response ?: "")
     * }
     * ```
     *
     * @param chatCompletionRequest The chat completion request with streaming enabled
     * @return A [Flow] of [ChatCompletionResponse] objects representing incremental updates
     */
    public fun chatCompletionStream(chatCompletionRequest: ChatCompletionRequest): Flow<ChatCompletionResponse>

    public suspend fun generateEmbed(generateEmbedRequest: EmbedRequest): EmbedResponse

    public suspend fun checkBlob(checkBlobRequest: CheckBlobRequest)

    public suspend fun copyModel(copyModelRequest: CopyModelRequest)

    public suspend fun createBlob(createBlobRequest: CreateBlobRequest)

    public suspend fun createModel(createModelRequest: CreateModelRequest): ProgressResponse

    public fun createModelStream(createModelRequest: CreateModelRequest): Flow<ProgressResponse>

    public suspend fun deleteModel(deleteModelRequest: DeleteModelRequest)

    public suspend fun listModels(): ListModelsResponse

    public suspend fun listRunningModels(): ListModelsResponse

    public suspend fun pullModel(pullModelRequest: PullModelRequest): ProgressResponse

    public fun pullModelStream(pullModelRequest: PullModelRequest): Flow<ProgressResponse>

    public suspend fun pushModel(pushModelRequest: PushModelRequest): ProgressResponse

    public fun pushModelStream(pushModelRequest: PushModelRequest): Flow<ProgressResponse>

    public suspend fun showModel(showModelRequest: ShowModelRequest): ShowModelResponse

    public suspend fun getMonitoring(): MonitoringResponse

    public suspend fun getVersion(): VersionResponse
}
