package org.nirmato.ollama.api

import kotlinx.coroutines.flow.Flow

public interface ModelsApi {
    /**
     * Ensures that the file blob used for a FROM or ADAPTER field exists on the server.
     * This is checking your Ollama server and not Ollama.ai.
     */
    public suspend fun checkBlob(checkBlobRequest: CheckBlobRequest)

    /**
     * Creates a model with another name from an existing model.
     */
    public suspend fun copyModel(copyModelRequest: CopyModelRequest)

    /**
     * Push a file to the Ollama server to create a "blob" (Binary Large Object).
     */
    public suspend fun createBlob(createBlobRequest: CreateBlobRequest)

    /**
     * Create a model from:
     * - Another model;
     * - A safetensors directory;
     * - A GGUF file.
     *
     * If you are creating a model from a safetensors directory or from a GGUF file, you must [create a blob](#create-a-blob)
     * for each of the files and then use the file name and SHA256 digest associated with each blob in the `files` field.
     *
     * @param createModelRequest Create a new model.
     */
    public suspend fun createModel(createModelRequest: CreateModelRequest): ProgressResponse

    /**
     * @see #createModel(CreateModelRequest)
     * */
    public fun createModelStream(createModelRequest: CreateModelRequest): Flow<ProgressResponse>

    /**
     * Delete a model and its data.
     */
    public suspend fun deleteModel(deleteModelRequest: DeleteModelRequest)

    /**
     * List models that are available locally.
     */
    public suspend fun listModels(): ListModelsResponse

    /**
     * List models that are running.
     */
    public suspend fun listRunningModels(): ListModelsResponse

    /**
     * Download a model from the ollama library.
     * Cancelled pulls are resumed from where they left off, and multiple calls will share the same download progress.
     */
    public suspend fun pullModel(pullModelRequest: PullModelRequest): ProgressResponse

    /**
     * @see pullModel
     */
    public fun pullModelStream(pullModelRequest: PullModelRequest): Flow<ProgressResponse>

    /**
     * Upload a model to a model library. Requires registering for ollama.ai and adding a public key first.
     *
     * @param pushModelRequest
     */
    public suspend fun pushModel(pushModelRequest: PushModelRequest): ProgressResponse

    /**
     * @see #pushModel(PushModelRequest)
     */
    public fun pushModelStream(pushModelRequest: PushModelRequest): Flow<ProgressResponse>

    /**
     * Show details about a model including model file, template, parameters, license, and system prompt.
     *
     * @param showModelRequest
     */
    public suspend fun showModel(showModelRequest: ShowModelRequest): ShowModelResponse
}
