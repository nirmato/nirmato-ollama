package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

@Serializable
public data class CheckBlobRequest(
    /** The SHA256 digest of the blob */
    @SerialName(value = "digest")
    @Required
    public val digest: String,
) {
    public companion object {
        /** A request for checking a blob. */
        public fun checkBlobRequest(block: CheckBlobRequestBuilder.() -> Unit): CheckBlobRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return CheckBlobRequestBuilder().apply(block).build()
        }

        public fun builder(): CheckBlobRequestBuilder = CheckBlobRequestBuilder()
    }

    /** Builder of [CheckBlobRequestBuilder] instances. */
    @OllamaDslMarker
    public class CheckBlobRequestBuilder() {
        private var digest: String? = null

        public fun digest(digest: String): CheckBlobRequestBuilder = apply { this.digest = digest }

        /** Create [CheckBlobRequest] instance. */
        public fun build(): CheckBlobRequest = CheckBlobRequest(
            digest = requireNotNull(digest) { "digest is required" },
        )
    }
}
