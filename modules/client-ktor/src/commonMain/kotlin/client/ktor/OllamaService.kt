package org.nirmato.ollama.client.ktor

import kotlinx.coroutines.flow.Flow
import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.contentType
import io.ktor.http.headers
import org.nirmato.ollama.api.ChatCompletionRequest
import org.nirmato.ollama.api.ChatCompletionResponse
import org.nirmato.ollama.api.ChatRequest
import org.nirmato.ollama.api.ChatResponse
import org.nirmato.ollama.api.CheckBlobRequest
import org.nirmato.ollama.api.CopyModelRequest
import org.nirmato.ollama.api.CreateBlobRequest
import org.nirmato.ollama.api.CreateModelRequest
import org.nirmato.ollama.api.DeleteModelRequest
import org.nirmato.ollama.api.EmbedRequest
import org.nirmato.ollama.api.EmbedResponse
import org.nirmato.ollama.api.ListModelsResponse
import org.nirmato.ollama.api.MonitoringResponse
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.api.ProgressResponse
import org.nirmato.ollama.api.PullModelRequest
import org.nirmato.ollama.api.PushModelRequest
import org.nirmato.ollama.api.ShowModelRequest
import org.nirmato.ollama.api.ShowModelResponse
import org.nirmato.ollama.api.VersionResponse

/**
 * OllamaService is a client for interacting with the Ollama API.
 *
 * It provides methods to perform various operations such as chat, chat completion, embedding generation,
 * model management (create, copy, delete, pull, push), and more.
 *
 * @property httpTransport The transport layer used for making HTTP requests.
 */
internal class OllamaService(
    private val httpTransport: HttpTransport,
) : OllamaApi {

    override suspend fun chat(chatRequest: ChatRequest): ChatResponse {
        val request = if (chatRequest.stream == true) chatRequest.copy(stream = false) else chatRequest

        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun chatStream(chatRequest: ChatRequest): Flow<ChatResponse> {
        val request = if (chatRequest.stream == false) chatRequest.copy(stream = true) else chatRequest

        return httpTransport.handleFlow<ChatResponse> {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }

    override suspend fun chatCompletion(chatCompletionRequest: ChatCompletionRequest): ChatCompletionResponse {
        val request = if (chatCompletionRequest.stream == true) chatCompletionRequest.copy(stream = false) else chatCompletionRequest

        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun chatCompletionStream(chatCompletionRequest: ChatCompletionRequest): Flow<ChatCompletionResponse> {
        val request = if (chatCompletionRequest.stream == false) chatCompletionRequest.copy(stream = true) else chatCompletionRequest

        return httpTransport.handleFlow<ChatCompletionResponse> {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }

    override suspend fun generateEmbed(generateEmbedRequest: EmbedRequest): EmbedResponse {
        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "embed")
            setBody(generateEmbedRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun checkBlob(checkBlobRequest: CheckBlobRequest) {
        return httpTransport.handleRequest {
            method = HttpMethod.Head
            url(path = "blobs/${checkBlobRequest.digest}")
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun copyModel(copyModelRequest: CopyModelRequest) {
        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "copy")
            setBody(copyModelRequest)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun createBlob(createBlobRequest: CreateBlobRequest) {
        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "blobs/${createBlobRequest.digest}")
            setBody(ByteArrayContent(createBlobRequest.body.value, ContentType.Application.OctetStream))
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun createModel(createModelRequest: CreateModelRequest): ProgressResponse {
        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "create")
            setBody(createModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun createModelStream(createModelRequest: CreateModelRequest): Flow<ProgressResponse> {
        val request = if (createModelRequest.stream == false) createModelRequest.copy(stream = true) else createModelRequest

        return httpTransport.handleFlow {
            method = HttpMethod.Post
            url(path = "create")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun deleteModel(deleteModelRequest: DeleteModelRequest) {
        return httpTransport.handleRequest {
            method = HttpMethod.Delete
            url(path = "delete")
            setBody(deleteModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun listModels(): ListModelsResponse {
        return httpTransport.handleRequest {
            method = HttpMethod.Get
            url(path = "tags")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun listRunningModels(): ListModelsResponse {
        return httpTransport.handleRequest {
            method = HttpMethod.Get
            url(path = "ps")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun pullModel(pullModelRequest: PullModelRequest): ProgressResponse {
        val request = if (pullModelRequest.stream == true) pullModelRequest.copy(stream = false) else pullModelRequest

        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "pull")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun pullModelStream(pullModelRequest: PullModelRequest): Flow<ProgressResponse> {
        val request = if (pullModelRequest.stream == false) pullModelRequest.copy(stream = true) else pullModelRequest

        return httpTransport.handleFlow {
            method = HttpMethod.Post
            url(path = "pull")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun pushModel(pushModelRequest: PushModelRequest): ProgressResponse {
        val request = if (pushModelRequest.stream == true) pushModelRequest.copy(stream = false) else pushModelRequest

        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "push")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun pushModelStream(pushModelRequest: PushModelRequest): Flow<ProgressResponse> {
        val request = if (pushModelRequest.stream == false) pushModelRequest.copy(stream = true) else pushModelRequest

        return httpTransport.handleFlow {
            method = HttpMethod.Post
            url(path = "push")
            setBody(request)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun showModel(showModelRequest: ShowModelRequest): ShowModelResponse {
        return httpTransport.handleRequest {
            method = HttpMethod.Post
            url(path = "show")
            setBody(showModelRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun getMonitoring(): MonitoringResponse {
        return httpTransport.handleRequest {
            method = HttpMethod.Get
            url(path = "version")
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun getVersion(): VersionResponse {
        return httpTransport.handleRequest {
            method = HttpMethod.Get
            url(path = "version")
            accept(ContentType.Application.Json)
        }
    }
}
