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
     * Create a blob from a file. Returns the server file path.
     */
    public suspend fun createBlob(createBlobRequest: CreateBlobRequest)

    /**
     * Create a model from a Modelfile.
     * It is recommended to set &#x60;modelfile&#x60; to the content of the Modelfile rather than just set &#x60;path&#x60;.
     * This is a requirement for remote create. Remote model creation should also create any file blobs, fields such as &#x60;FROM&#x60; and &#x60;ADAPTER&#x60;,
     * explicitly with the server using Create a Blob and the value to the path indicated in the response.
     *
     * @param createModelRequest Create a new model from a Modelfile.
     */
    public suspend fun createModel(createModelRequest: CreateModelRequest): CreateModelResponse

    /** @see #createModel(CreateModelRequest) */
    public fun createModelStream(createModelRequest: CreateModelRequest): Flow<CreateModelResponse>

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
    public suspend fun listRunningModels(): ProcessResponse

    /**
     * Download a model from the ollama library.
     * Cancelled pulls are resumed from where they left off, and multiple calls will share the same download progress.
     */
    public suspend fun pullModel(pullModelRequest: PullModelRequest): PullModelResponse

    /**
     * Upload a model to a model library. Requires registering for ollama.ai and adding a public key first.
     *
     * @param pushModelRequest
     */
    public suspend fun pushModel(pushModelRequest: PushModelRequest): PushModelResponse

    /** @see #pushModel(PushModelRequest) */
    public fun pushModelStream(pushModelRequest: PushModelRequest): Flow<PushModelResponse>

    /**
     * Show details about a model including model file, template, parameters, license, and system prompt.
     *
     * @param showModelRequest
     */
    public suspend fun showModel(showModelRequest: ShowModelRequest): ShowModelResponse
}
