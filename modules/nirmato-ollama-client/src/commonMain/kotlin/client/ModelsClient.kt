package org.nirmato.ollama.client

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
import org.nirmato.ollama.api.ModelListResponse
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.OllamaModelCard
import org.nirmato.ollama.api.ProcessResponse
import org.nirmato.ollama.api.PullModelRequest
import org.nirmato.ollama.api.PullModelResponse
import org.nirmato.ollama.api.PushModelRequest
import org.nirmato.ollama.api.PushModelResponse
import org.nirmato.ollama.api.ShowModelInformationRequest
import org.nirmato.ollama.client.internal.RequestHandler
import org.nirmato.ollama.client.internal.handle

internal class ModelsClient internal constructor(private val requestHandler: RequestHandler) : ModelsApi {
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

    override suspend fun deleteModel(deleteModelRequest: DeleteModelRequest) {
        return requestHandler.handle {
            method = HttpMethod.Delete
            url(path = "delete")
            setBody(deleteModelRequest)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun listModels(): ModelListResponse {
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
            setBody(pushModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun showModelInformation(modelInfoRequest: ShowModelInformationRequest): OllamaModelCard {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "show")
            setBody(modelInfoRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
