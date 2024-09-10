package org.nirmato.ollama.api

import org.nirmato.ollama.api.CopyModelRequest.CopyModelRequestBuilder
import org.nirmato.ollama.api.CreateBlobRequest.CreateBlobRequestBuilder
import org.nirmato.ollama.api.CreateModelRequest.CreateModelRequestBuilder
import org.nirmato.ollama.api.DeleteModelRequest.DeleteModelRequestBuilder
import org.nirmato.ollama.api.PullModelRequest.PullModelRequestBuilder
import org.nirmato.ollama.api.PushModelRequest.PushModelRequestBuilder
import org.nirmato.ollama.api.ShowModelInformationRequest.ShowModelInformationRequestBuilder

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

    /**
     * Delete a model and its data.
     */
    public suspend fun deleteModel(deleteModelRequest: DeleteModelRequest)

    /**
     * List models that are available locally.
     */
    public suspend fun listModels(): ModelListResponse

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
     * Upload a model to a model library.
     * Requires registering for ollama.ai and adding a public key first.
     *
     * @param pushModelRequest
     */
    public suspend fun pushModel(pushModelRequest: PushModelRequest): PushModelResponse

    /**
     * Show details about a model including modelfile, template, parameters, license, and system prompt.
     *
     * @param modelInfoRequest
     */
    public suspend fun showModelInformation(modelInfoRequest: ShowModelInformationRequest): OllamaModelCard
}


public suspend fun ModelsApi.copyModel(block: CopyModelRequestBuilder.() -> Unit) {
    val copyModelRequest = CopyModelRequestBuilder().apply(block).build()
    copyModel(copyModelRequest)
}

public suspend fun ModelsApi.createBlob(block: CreateBlobRequestBuilder.() -> Unit) {
    val createBlobRequest = CreateBlobRequestBuilder().apply(block).build()
    createBlob(createBlobRequest)
}

public suspend fun ModelsApi.createModel(block: CreateModelRequestBuilder.() -> Unit): CreateModelResponse {
    val createModelRequest = CreateModelRequestBuilder().apply(block).build()
    return createModel(createModelRequest)
}

public suspend fun ModelsApi.deleteModel(block: DeleteModelRequestBuilder.() -> Unit) {
    val deleteModelRequest = DeleteModelRequestBuilder().apply(block).build()
    deleteModel(deleteModelRequest)
}

public suspend fun ModelsApi.pullModel(block: PullModelRequestBuilder.() -> Unit): PullModelResponse {
    val pullModelRequest = PullModelRequestBuilder().apply(block).build()
    return pullModel(pullModelRequest)
}

public suspend fun ModelsApi.pushModel(block: PushModelRequestBuilder.() -> Unit): PushModelResponse {
    val pushModelRequest = PushModelRequestBuilder().apply(block).build()
    return pushModel(pushModelRequest)
}

public suspend fun ModelsApi.showModelInformation(block: ShowModelInformationRequestBuilder.() -> Unit): OllamaModelCard {
    val modelInfoRequest = ShowModelInformationRequestBuilder().apply(block).build()
    return showModelInformation(modelInfoRequest)
}
