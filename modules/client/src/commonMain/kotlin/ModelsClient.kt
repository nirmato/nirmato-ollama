package org.nirmato.ollama.client

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
import org.nirmato.ollama.api.CreateModelResponse
import org.nirmato.ollama.api.DeleteModelRequest
import org.nirmato.ollama.api.ListModelsResponse
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.ProcessResponse
import org.nirmato.ollama.api.PullModelRequest
import org.nirmato.ollama.api.PullModelResponse
import org.nirmato.ollama.api.PushModelRequest
import org.nirmato.ollama.api.PushModelResponse
import org.nirmato.ollama.api.ShowModelRequest
import org.nirmato.ollama.api.ShowModelResponse
import org.nirmato.ollama.client.http.HttpClientHandler
import org.nirmato.ollama.client.http.handle
import org.nirmato.ollama.client.http.handleFlow

public class ModelsClient(private val requestHandler: HttpClientHandler) : ModelsApi {
    override suspend fun checkBlob(checkBlobRequest: CheckBlobRequest) {
        return requestHandler.handle {
            method = HttpMethod.Head
            url(path = "blobs/${checkBlobRequest.digest}")
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun copyModel(copyModelRequest: CopyModelRequest) {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "copy")
            setBody(copyModelRequest)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun createBlob(createBlobRequest: CreateBlobRequest) {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "blobs/${createBlobRequest.digest}")
            setBody(ByteArrayContent(createBlobRequest.body.value, ContentType.Application.OctetStream))
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun createModel(createModelRequest: CreateModelRequest): CreateModelResponse {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "create")
            setBody(createModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun createModelFlow(createModelRequest: CreateModelRequest): Flow<CreateModelResponse> {
        return requestHandler.handleFlow {
            method = HttpMethod.Post
            url(path = "create")
            setBody(createModelRequest.toStreamRequest(true))
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun deleteModel(deleteModelRequest: DeleteModelRequest) {
        return requestHandler.handle {
            method = HttpMethod.Delete
            url(path = "delete")
            setBody(deleteModelRequest)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun listModels(): ListModelsResponse {
        return requestHandler.handle {
            method = HttpMethod.Get
            url(path = "tags")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun listRunningModels(): ProcessResponse {
        return requestHandler.handle {
            method = HttpMethod.Get
            url(path = "ps")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun pullModel(pullModelRequest: PullModelRequest): PullModelResponse {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "pull")
            setBody(pullModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun pushModel(pushModelRequest: PushModelRequest): PushModelResponse {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "push")
            setBody(pushModelRequest.toStreamRequest(false))
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun pushModelFlow(pushModelRequest: PushModelRequest): Flow<PushModelResponse> {
        return requestHandler.handleFlow {
            method = HttpMethod.Post
            url(path = "push")
            setBody(pushModelRequest.toStreamRequest(true))
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun showModel(showModelRequest: ShowModelRequest): ShowModelResponse {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "show")
            setBody(showModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
