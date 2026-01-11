package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow

/**
 * Ollama API interface for interacting with the Ollama server.
 *
 * This interface defines the methods available for sending chat requests, generating embeddings,
 * managing models, and retrieving server information.
 *
 * Example usage:
 * ```kotlin
 * val ollamaClient: OllamaApi = ...
 * val response = ollamaClient.chat(chatRequest { ... })
 * ```
 */
public interface OllamaApi {

    /**
     * Sends a chat request.
     *
     * Generate the next chat message in a conversation between a user and an assistant.
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
     * Streams chat responses chunk by chunk.
     *
     * Generate the next chat message in a conversation between a user and an assistant.
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

    /**
     * Generates vector embedding representing the input text.
     *
     * Interacts with the Ollama API to create embeddings, which are vector representations of input data.
     * Embeddings are commonly used in machine learning tasks such as similarity search, clustering, and classification.
     *
     * Example usage:
     * ```kotlin
     * val generateEmbedRequest = embedRequest {
     *   model("tinyllama")
     *   input(EmbeddedList(listOf(EmbeddedText("Why is the sky blue?"), EmbeddedText("Why is the grass green?"))))
     * }
     *
     * val response = ollamaClient.generateEmbed(generateEmbedRequest)
     *
     * @param generateEmbedRequest The request object containing the input data for which the embedding is to be generated.
     * @return An [EmbedResponse] containing the generated embedding.
     */
    public suspend fun generateEmbed(generateEmbedRequest: EmbedRequest): EmbedResponse

    /**
     * Checks the status of a blob in the Ollama API.
     *
     * This function allows you to verify the existence or state of a blob resource
     * in the system. It is typically used to ensure that a blob is available or
     * correctly configured before performing further operations.
     *
     * Example usage:
     * ```kotlin
     * val checkBlobRequest = checkBlobRequest {
     *   digest("sha256:d4dd5fe90054a4539584cd5f7e612a7121a3b8daa9b68a3aae929317251810b4")
     * }
     *
     * ollamaClient.checkBlob(checkBlobRequest)
     * ```
     *
     * @param checkBlobRequest The request object containing the details of the blob to be checked.
     */
    public suspend fun checkBlob(checkBlobRequest: CheckBlobRequest)

    /**
     * Creates a new blob in the Ollama API.
     *
     * This function allows you to upload or define a blob resource in the system.
     * Blobs are typically used to store binary data or other resources required by models.
     *
     * Example usage:
     * ```kotlin
     * val createBlobRequest = createBlobRequest {
     *   digest("sha256:d4dd5fe90054a4539584cd5f7e612a7121a3b8daa9b68a3aae929317251810b4")
     *   body(OctetByteArray("newblob".toByteArray()))
     * }
     *
     * ollamaClient.createBlob(createBlobRequest)
     * ```
     *
     * @param createBlobRequest The request object containing the details of the blob to be created.
     */
    public suspend fun createBlob(createBlobRequest: CreateBlobRequest)

    /**
     * Copies a model in the Ollama API.
     *
     * This function allows you to duplicate an existing model within the system.
     * It is useful for creating backups or preparing a model for further modifications.
     *
     * Example usage:
     * ```kotlin
     * val copyModelRequest = copyModelRequest {
     *   sourceModel("sourceModelName")
     *   targetModel("targetModelName")
     * }
     *
     * ollamaClient.copyModel(copyModelRequest)
     * ```
     *
     * @param copyModelRequest The request object containing the source and target model details.
     */
    public suspend fun copyModel(copyModelRequest: CopyModelRequest)

    /**
     * Creates a new model in the Ollama API.
     *
     * This function allows you to define and upload a new model to the system.
     * Models are typically used for machine learning tasks such as inference or training.
     *
     * Example usage:
     * ```kotlin
     * val createModelRequest = createModelRequest {
     *   model("newModelName")
     *   from("sourceModelName")
     *   system("System prompt for the new model")
     * }
     *
     * val progressResponse = ollamaClient.createModel(createModelRequest)
     * ```
     *
     * @param createModelRequest The request object containing the details of the model to be created.
     * @return A [ProgressResponse] indicating the progress of the model creation process.
     */
    public suspend fun createModel(createModelRequest: CreateModelRequest): ProgressResponse

    /**
     * Streams the progress of model creation in the Ollama API.
     *
     * This function provides real-time updates on the model creation process,
     * allowing you to monitor the progress as it happens.
     *
     * Example usage:
     * ```kotlin
     * val createModelRequest = createModelRequest {
     *   model("newModelName")
     *   from("sourceModelName")
     *   system("System prompt for the new model")
     * }
     *
     * ollamaClient.createModelStream(createModelRequest).collect { progress ->
     *   println("Progress: $it")
     * }
     * ```
     *
     * @param createModelRequest The request object containing the details of the model to be created.
     * @return A [Flow] of [ProgressResponse] objects representing incremental updates.
     */
    public fun createModelStream(createModelRequest: CreateModelRequest): Flow<ProgressResponse>

    /**
     * Deletes a model in the Ollama API.
     *
     * This function allows you to remove an existing model from the system.
     * It is typically used when a model is no longer needed or requires replacement.
     *
     * Example usage:
     * ```kotlin
     * val deleteModelRequest = deleteModelRequest {
     *   model("modelNameToDelete")
     * }
     *
     * ollamaClient.deleteModel(deleteModelRequest)
     *
     * @param deleteModelRequest The request object containing the details of the model to be deleted.
     */
    public suspend fun deleteModel(deleteModelRequest: DeleteModelRequest)

    /**
     * Lists all models in the Ollama API.
     *
     * This function retrieves a list of all available models in the system.
     * It is useful for discovering existing models and their configurations.
     *
     * Example usage:
     * ```kotlin
     * val response = ollamaClient.listModels()
     * println(response.models)
     * ```
     *
     * @return A [ListModelsResponse] containing the details of all models.
     */
    public suspend fun listModels(): ListModelsResponse

    /**
     * Lists all running models in the Ollama API.
     *
     * This function retrieves a list of all models that are currently running in the system.
     * It is useful for monitoring active models and their statuses.
     *
     * Example usage:
     * ```kotlin
     * val response = ollamaClient.listRunningModels()
     * println(response.models)
     * ```
     *
     * @return A [ListModelsResponse] containing the details of all running models.
     */
    public suspend fun listRunningModels(): ListModelsResponse

    /**
     * Pulls a model from the Ollama API.
     *
     * This function allows you to download or synchronize a model from the server to your local environment.
     * It is typically used to ensure that you have the latest version of a model available for use.
     *
     * Example usage:
     * ```kotlin
     * val pullModelRequest = pullModelRequest {
     *   name("modelNameToPull")
     * }
     *
     * val progressResponse = ollamaClient.pullModel(pullModelRequest)
     * ```
     *
     * @param pullModelRequest The request object containing the details of the model to be pulled.
     * @return A [ProgressResponse] indicating the progress of the pull operation.
     */
    public suspend fun pullModel(pullModelRequest: PullModelRequest): ProgressResponse

    /**
     * Streams the progress of pulling a model from the Ollama API.
     *
     * This function provides real-time updates on the model pulling process,
     * allowing you to monitor the progress as it happens.
     *
     * Example usage:
     * ```kotlin
     * val pullModelRequest = pullModelRequest {
     *   name("modelNameToPull")
     * }
     *
     * ollamaClient.pullModelStream(pullModelRequest).collect {
     *   println(it)
     * }
     * ```
     *
     * @param pullModelRequest The request object containing the details of the model to be pulled.
     * @return A [Flow] of [ProgressResponse] objects representing incremental updates.
     */
    public fun pullModelStream(pullModelRequest: PullModelRequest): Flow<ProgressResponse>

    /**
     * Pushes a model to the Ollama API.
     *
     * This function allows you to upload or synchronize a model from your local environment to the server.
     * It is typically used to share models or update existing ones on the server.
     *
     * Example usage:
     * ```kotlin
     * val pushModelRequest = pushModelRequest {
     *   model("modelNameToPush")
     * }
     *
     * val progressResponse = ollamaClient.pushModel(pushModelRequest)
     * ```
     *
     * @param pushModelRequest The request object containing the details of the model to be pushed.
     * @return A [ProgressResponse] indicating the progress of the push operation.
     */
    public suspend fun pushModel(pushModelRequest: PushModelRequest): ProgressResponse

    /**
     * Streams the progress of pushing a model to the Ollama API.
     *
     * This function provides real-time updates on the model pushing process,
     * allowing you to monitor the progress as it happens.
     *
     * Example usage:
     * ```kotlin
     * val pushModelRequest = pushModelRequest {
     *   model("modelNameToPush")
     * }
     *
     * ollamaClient.pushModelStream(pushModelRequest).collect {
     *   println(it)
     * }
     * ```
     *
     * @param pushModelRequest The request object containing the details of the model to be pushed.
     * @return A [Flow] of [ProgressResponse] objects representing incremental updates.
     */
    public fun pushModelStream(pushModelRequest: PushModelRequest): Flow<ProgressResponse>

    /**
     * Shows the details of a model in the Ollama API.
     *
     * This function retrieves detailed information about a specific model.
     *
     * Example usage:
     * ```kotlin
     * val showModelRequest = showModelRequest {
     *   name("modelNameToShow")
     * }
     *
     * val response = ollamaClient.showModel(showModelRequest)
     * println(response.modelInfo)
     * ```
     *
     * @param showModelRequest The request object containing the details of the model to be shown.
     * @return A [ShowModelResponse] containing the model's details.
     */
    public suspend fun showModel(showModelRequest: ShowModelRequest): ShowModelResponse

    /**
     * Retrieves the monitoring information from the Ollama API.
     *
     * This function provides insights into the current state of the Ollama server,
     * including resource usage and performance metrics.
     *
     * Example usage:
     * ```kotlin
     * val monitoringResponse = ollamaClient.getMonitoring()
     * println(monitoringResponse.status)
     * ```
     *
     * @return A [MonitoringResponse] containing the monitoring data.
     */
    public suspend fun getMonitoring(): MonitoringResponse

    /**
     * Retrieves the version information from the Ollama API.
     *
     * This function provides details about the current version of the Ollama server,
     * including version number and build information.
     *
     * Example usage:
     * ```kotlin
     * val versionResponse = ollamaClient.getVersion()
     * println(versionResponse.version)
     * ```
     *
     * @return A [VersionResponse] containing the version details.
     */
    public suspend fun getVersion(): VersionResponse
}
