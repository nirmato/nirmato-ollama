package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

@Serializable
public data class CreateBlobRequest(
    /** The SHA256 digest of the blob */
    @SerialName(value = "digest")
    @Required
    val digest: String,

    @SerialName(value = "body")
    @Required
    val body: OctetByteArray,
) {
    public companion object {
        /** A request for creating a blob. */
        public fun createBlobRequest(block: CreateBlobRequestBuilder.() -> Unit): CreateBlobRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return CreateBlobRequestBuilder().apply(block).build()
        }

        public fun builder(): CreateBlobRequestBuilder = CreateBlobRequestBuilder()
    }

    /** Builder of [CreateBlobRequestBuilder] instances. */
    @OllamaDslMarker
    public class CreateBlobRequestBuilder {
        private var digest: String? = null
        private var body: OctetByteArray? = null

        public fun digest(digest: String): CreateBlobRequestBuilder = apply { this.digest = digest }
        public fun body(body: OctetByteArray): CreateBlobRequestBuilder = apply { this.body = body }

        /** Create [CreateBlobRequest] instance. */
        public fun build(): CreateBlobRequest = CreateBlobRequest(
            digest = requireNotNull(digest) { "digest is required" },
            body = requireNotNull(body) { "body is required" }
        )
    }
}
