package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow

public interface OllamaApi {

    public suspend fun chat(chatRequest: ChatRequest): ChatResponse

    public fun chatStream(chatRequest: ChatRequest): Flow<ChatResponse>

    public suspend fun completion(completionRequest: CompletionRequest): CompletionResponse

    public fun completionStream(completionRequest: CompletionRequest): Flow<CompletionResponse>

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
