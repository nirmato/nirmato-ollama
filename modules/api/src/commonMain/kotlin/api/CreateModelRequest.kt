package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Create model request object.
 */
@Serializable
public data class CreateModelRequest(

    /** The name of the model to create. */
    @SerialName(value = "model")
    @Required
    val model: String,

    /** The name of an existing model to create the new model from. */
    @SerialName(value = "from")
    val from: String? = null,

    /** A dictionary of file names to SHA256 digests of blobs to create the model from. */
    @SerialName(value = "files")
    val files: Map<String, String>? = null,

    /** A dictionary of file names to SHA256 digests of blobs for LORA adapters. */
    @SerialName(value = "adapters")
    val adapters: Map<String, String>? = null,

    /** The prompt template for the model. */
    @SerialName(value = "template")
    val template: String? = null,

    /** The license or licenses for the model. */
    @SerialName(value = "license")
    val license: LicenseModel? = null,

    /** The system prompt for the model. */
    @SerialName(value = "system")
    val system: String? = null,

    /** The parameters for the model. */
    @SerialName(value = "parameters")
    val parameters: Map<String, String>? = null,

    /** The list of message objects used to create a conversation. */
    @SerialName(value = "messages")
    val messages: List<Message>? = null,

    /** Quantize a non-quantized (e.g. float16) model. */
    @SerialName(value = "quantize")
    val quantize: Quantize? = null,
) {

    public sealed interface LicenseModel {
        public data class SingleLicenseModel(val license: String) : LicenseModel
        public data class MultipleLicenseModel(val licenses: List<String>) : LicenseModel
    }

    @Serializable
    public enum class Quantize {
        @SerialName(value = "q2_K")
        Q2_K,
        @SerialName(value = "q3_K_L")
        Q3_K_L,
        @SerialName(value = "q3_K_M")
        Q3_K_M,
        @SerialName(value = "q3_K_S")
        Q3_K_S,
        @SerialName(value = "q4_0")
        Q4_0,
        @SerialName(value = "q4_1")
        Q4_1,
        @SerialName(value = "q4_K_M")
        Q4_K_M,
        @SerialName(value = "q4_K_S")
        Q4_K_S,
        @SerialName(value = "q5_0")
        Q5_0,
        @SerialName(value = "q5_1")
        Q5_1,
        @SerialName(value = "q5_K_M")
        Q5_K_M,
        @SerialName(value = "q5_K_S")
        Q5_K_S,
        @SerialName(value = "q6_K")
        Q6_K,
        @SerialName(value = "q8_0")
        Q8_0,
    }

    public companion object {
        /** A request for creating a model. */
        public fun createModelRequest(block: CreateModelRequestBuilder.() -> Unit): CreateModelRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return CreateModelRequestBuilder().apply(block).build()
        }
    }

    /** Builder of [CreateModelRequest] instances. */
    @OllamaDsl
    public class CreateModelRequestBuilder {
        public var model: String? = null
        public var from: String? = null
        public var files: Map<String, String>? = null
        public var adapters: Map<String, String>? = null
        public var template: String? = null
        public var license: LicenseModel? = null
        public var system: String? = null
        public var parameters: Map<String, String>? = null
        public var messages: List<Message>? = null
        public var quantize: Quantize? = null

        /** Create [CreateModelRequest] instance. */
        public fun build(): CreateModelRequest = CreateModelRequest(
            model = requireNotNull(model) { "model is required" },
            from = from,
            files = files,
            adapters = adapters,
            template = template,
            license = license,
            system = system,
            parameters = parameters,
            messages = messages,
            quantize = quantize,
        )
    }
}
