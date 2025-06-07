package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

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

    /** If `false` the response will be returned as a single response object, otherwise, the response will be streamed as a series of objects.  */
    @SerialName(value = "stream")
    val stream: Boolean? = false,
) {

    public sealed interface LicenseModel {
        public data class SingleLicenseModel(val license: String) : LicenseModel
        public data class MultipleLicenseModel(val licenses: List<String>) : LicenseModel
    }

    @Serializable
    public enum class Quantize {
        @SerialName(value = "q4_K_M")
        Q4_K_M,
        @SerialName(value = "q4_K_S")
        Q4_K_S,
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

        public fun builder(): CreateModelRequestBuilder = CreateModelRequestBuilder()
        public fun builder(createModelRequest: CreateModelRequest): CreateModelRequestBuilder = CreateModelRequestBuilder(createModelRequest)
    }

    /** Builder of [CreateModelRequest] instances. */
    @OllamaDslMarker
    public class CreateModelRequestBuilder() {
        private var model: String? = null
        private var from: String? = null
        private var files: Map<String, String>? = null
        private var adapters: Map<String, String>? = null
        private var template: String? = null
        private var license: LicenseModel? = null
        private var system: String? = null
        private var parameters: Map<String, String>? = null
        private var messages: List<Message>? = null
        private var quantize: Quantize? = null
        private var stream: Boolean? = false

        public constructor(createModelRequest: CreateModelRequest) : this() {
            model = createModelRequest.model
            from = createModelRequest.from
            files = createModelRequest.files
            adapters = createModelRequest.adapters
            template = createModelRequest.template
            license = createModelRequest.license
            system = createModelRequest.system
            parameters = createModelRequest.parameters
            messages = createModelRequest.messages
            quantize = createModelRequest.quantize
            stream = createModelRequest.stream
        }

        public fun model(model: String): CreateModelRequestBuilder = apply { this.model = model }
        public fun from(from: String): CreateModelRequestBuilder = apply { this.from = from }
        public fun files(files: Map<String, String>): CreateModelRequestBuilder = apply { this.files = files }
        public fun adapters(adapters: Map<String, String>): CreateModelRequestBuilder = apply { this.adapters = adapters }
        public fun template(template: String): CreateModelRequestBuilder = apply { this.template = template }
        public fun license(license: LicenseModel): CreateModelRequestBuilder = apply { this.license = license }
        public fun system(system: String): CreateModelRequestBuilder = apply { this.system = system }
        public fun parameters(parameters: Map<String, String>): CreateModelRequestBuilder = apply { this.parameters = parameters }
        public fun messages(messages: List<Message>): CreateModelRequestBuilder = apply { this.messages = messages }
        public fun quantize(quantize: Quantize): CreateModelRequestBuilder = apply { this.quantize = quantize }
        public fun stream(stream: Boolean): CreateModelRequestBuilder = apply { this.stream = stream }

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
            stream = stream,
        )
    }
}
