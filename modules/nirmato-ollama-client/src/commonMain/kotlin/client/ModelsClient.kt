package org.nirmato.ollama.client

import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.contentType
import org.nirmato.ollama.api.CopyModelRequest
import org.nirmato.ollama.api.CreateModelRequest
import org.nirmato.ollama.api.CreateModelResponse
import org.nirmato.ollama.api.DeleteModelRequest
import org.nirmato.ollama.api.ModelInfo
import org.nirmato.ollama.api.ModelInfoRequest
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.ModelsResponse
import org.nirmato.ollama.api.ProcessResponse
import org.nirmato.ollama.api.PullModelRequest
import org.nirmato.ollama.api.PullModelResponse
import org.nirmato.ollama.api.PushModelRequest
import org.nirmato.ollama.api.PushModelResponse
import org.nirmato.ollama.infrastructure.OctetByteArray
import org.nirmato.ollama.internal.HttpRequester
import org.nirmato.ollama.internal.processRequest

internal class ModelsClient internal constructor(private val requester: HttpRequester) : ModelsApi {
    override suspend fun checkBlob(digest: String) {
        return requester.processRequest {
            method = HttpMethod.Head
            url(path = "blobs/${digest}")
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun copyModel(copyModelRequest: CopyModelRequest) {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "copy")
            setBody(copyModelRequest)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun createBlob(digest: String, body: OctetByteArray) {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "blobs/${digest}")
            setBody(ByteArrayContent(body.value, ContentType.Application.OctetStream))
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun createModel(createModelRequest: CreateModelRequest): CreateModelResponse {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "create")
            setBody(createModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun deleteModel(deleteModelRequest: DeleteModelRequest) {
        return requester.processRequest {
            method = HttpMethod.Delete
            url(path = "delete")
            setBody(deleteModelRequest)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun listModels(): ModelsResponse {
        return requester.processRequest {
            method = HttpMethod.Get
            url(path = "tags")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun listRunningModels(): ProcessResponse {
        return requester.processRequest {
            method = HttpMethod.Get
            url(path = "ps")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun pullModel(pullModelRequest: PullModelRequest): PullModelResponse {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "pull")
            setBody(pullModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun pushModel(pushModelRequest: PushModelRequest): PushModelResponse {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "push")
            setBody(pushModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun showModelInfo(modelInfoRequest: ModelInfoRequest): ModelInfo {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "show")
            setBody(modelInfoRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
