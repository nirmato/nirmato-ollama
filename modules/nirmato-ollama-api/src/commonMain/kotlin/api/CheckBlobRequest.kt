package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

@Serializable
public data class CheckBlobRequest(
    /** The SHA256 digest of the blob */
    @SerialName(value = "digest")
    @Required
    val digest: String,
) {
    public companion object {
        /** A request for checking a blob. */
        public fun checkBlobRequest(block: CheckBlobRequestBuilder.() -> Unit): CheckBlobRequest = CheckBlobRequestBuilder().apply(block).build()
    }

    /** Builder of [CheckBlobRequestBuilder] instances. */
    @OllamaDsl
    public class CheckBlobRequestBuilder {
        public var digest: String? = null

        /** Create [CheckBlobRequest] instance. */
        public fun build(): CheckBlobRequest = CheckBlobRequest(
            digest = requireNotNull(digest) { "digest is required" },
        )
    }
}
