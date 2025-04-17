package org.nirmato.ollama.client.ktor

import kotlinx.coroutines.flow.Flow
import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.contentType
import org.nirmato.ollama.api.CheckBlobRequest
import org.nirmato.ollama.api.CopyModelRequest
import org.nirmato.ollama.api.CreateBlobRequest
import org.nirmato.ollama.api.CreateModelRequest
import org.nirmato.ollama.api.DeleteModelRequest
import org.nirmato.ollama.api.ListModelsResponse
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.ProgressResponse
import org.nirmato.ollama.api.PullModelRequest
import org.nirmato.ollama.api.PushModelRequest
import org.nirmato.ollama.api.ShowModelRequest
import org.nirmato.ollama.api.ShowModelResponse
import org.nirmato.ollama.client.ktor.internal.http.KtorHttpClient
import org.nirmato.ollama.client.ktor.internal.http.handleFlow
import org.nirmato.ollama.client.ktor.internal.http.perform

public open class ModelsClient internal constructor(private val httpClient: KtorHttpClient) : ModelsApi {
    /**
     * Ensures that the file blob used for a FROM or ADAPTER field exists on the server.
     * This is checking your Ollama server and not Ollama.ai.
     */
    public override suspend fun checkBlob(checkBlobRequest: CheckBlobRequest) {
        return httpClient.perform {
            method = HttpMethod.Head
            url(path = "blobs/${checkBlobRequest.digest}")
            contentType(ContentType.Application.Json)
        }
    }

    /**
     * Creates a model with another name from an existing model.
     */
    public override suspend fun copyModel(copyModelRequest: CopyModelRequest) {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "copy")
            setBody(copyModelRequest)
            contentType(ContentType.Application.Json)
        }
    }

    /**
     * Create a blob from a file. Returns the server file path.
     */
    public override suspend fun createBlob(createBlobRequest: CreateBlobRequest) {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "blobs/${createBlobRequest.digest}")
            setBody(ByteArrayContent(createBlobRequest.body.value, ContentType.Application.OctetStream))
            contentType(ContentType.Application.Json)
        }
    }

    /**
     * Create a model from a Modelfile.
     * It is recommended to set &#x60;modelfile&#x60; to the content of the Modelfile rather than just set &#x60;path&#x60;.
     * This is a requirement for remote create. Remote model creation should also create any file blobs, fields such as &#x60;FROM&#x60; and &#x60;ADAPTER&#x60;,
     * explicitly with the server using Create a Blob and the value to the path indicated in the response.
     *
     * @param createModelRequest Create a new model from a Modelfile.
     */
    public override suspend fun createModel(createModelRequest: CreateModelRequest): ProgressResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "create")
            setBody(createModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * @see #createModel(CreateModelRequest)
     */
    public override fun createModelStream(createModelRequest: CreateModelRequest): Flow<ProgressResponse> {
        return httpClient.handleFlow {
            method = HttpMethod.Post
            url(path = "create")
            setBody(CreateModelRequest.builder(createModelRequest).stream(true).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * Delete a model and its data.
     */
    public override suspend fun deleteModel(deleteModelRequest: DeleteModelRequest) {
        return httpClient.perform {
            method = HttpMethod.Delete
            url(path = "delete")
            setBody(deleteModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * List models that are available locally.
     */
    public override suspend fun listModels(): ListModelsResponse {
        return httpClient.perform {
            method = HttpMethod.Get
            url(path = "tags")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * List models that are running.
     */
    public override suspend fun listRunningModels(): ListModelsResponse {
        return httpClient.perform {
            method = HttpMethod.Get
            url(path = "ps")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * Download a model from the ollama library.
     * Cancelled pulls are resumed from where they left off, and multiple calls will share the same download progress.
     */
    public override suspend fun pullModel(pullModelRequest: PullModelRequest): ProgressResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "pull")
            setBody(pullModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * @see #pullModel(PullModelRequest)
     */
    public override fun pullModelStream(pullModelRequest: PullModelRequest): Flow<ProgressResponse> {
        return httpClient.handleFlow {
            method = HttpMethod.Post
            url(path = "pull")
            setBody(PullModelRequest.builder(pullModelRequest).stream(true).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * Upload a model to a model library. Requires registering for ollama.ai and adding a public key first.
     *
     * @param pushModelRequest
     */
    public override suspend fun pushModel(pushModelRequest: PushModelRequest): ProgressResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "push")
            setBody(PushModelRequest.builder(pushModelRequest).stream(true).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * @see #pushModel(PushModelRequest)
     */
    public override fun pushModelStream(pushModelRequest: PushModelRequest): Flow<ProgressResponse> {
        return httpClient.handleFlow {
            method = HttpMethod.Post
            url(path = "push")
            setBody(PushModelRequest.builder(pushModelRequest).stream(true).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * Show details about a model including model file, template, parameters, license, and system prompt.
     *
     * @param showModelRequest
     */
    public override suspend fun showModel(showModelRequest: ShowModelRequest): ShowModelResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "show")
            setBody(showModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
