package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Additional model parameters listed in the documentation for the Modelfile, such as `temperature`.
 */
@Serializable
public data class RequestOptions(
    /** Enable Mirostat sampling for controlling perplexity. (default: 0, 0 = disabled, 1 = Mirostat, 2 = Mirostat 2.0)  */
    @SerialName(value = "mirostat")
    val mirostat: Int? = null,

    /**
     * Influences how quickly the algorithm responds to feedback from the generated text.
     * A lower learning rate will result in slower adjustments, while a higher learning rate will make the algorithm more responsive. (Default: 0.1)
     */
    @SerialName(value = "mirostat_eta")
    val mirostatEta: Float? = null,
    /**
     * Controls the balance between coherence and diversity of the output.
     * A lower value will result in more focused and coherent text. (Default: 5.0)
     */
    @SerialName(value = "mirostat_tau")
    val mirostatTau: Float? = null,

    /** Sets the size of the context window used to generate the next token. (Default: 2048) */
    @SerialName(value = "num_ctx")
    val numCtx: Int? = null,

    /** Sets how far back for the model to look back to prevent repetition. (Default: 64, 0 = disabled, -1 = num_ctx)  */
    @SerialName(value = "repeat_last_n")
    val repeatLastN: Int? = null,

    /**
     * Sets how strongly to penalize repetitions.
     * A higher value (e.g., 1.5) will penalize repetitions more strongly, while a lower value (e.g., 0.9) will be more lenient. (Default: 1.1)
     */
    @SerialName(value = "repeat_penalty")
    val repeatPenalty: Float? = null,

    /**
     * The temperature of the model.
     * Increasing the temperature will make the model answer more creatively. (Default: 0.8)
     */
    @SerialName(value = "temperature")
    val temperature: Float? = null,

    /**
     * Sets the random number seed to use for generation.
     * Setting this to a specific number will make the model generate the same text for the same prompt. (Default: 0)
     */
    @SerialName(value = "seed")
    val seed: Int? = null,

    /**
     * Sets the stop sequences to use. When this pattern is encountered the LLM will stop generating text and return.
     * Multiple stop patterns may be set by specifying multiple separate stop parameters in a modelfile.
     * The returned text will not contain the stop sequence.
     */
    @SerialName(value = "stop")
    val stop: List<String>? = null,

    /**
     * Tail free sampling is used to reduce the impact of less probable tokens from the output.
     * A higher value (e.g., 2.0) will reduce the impact more, while a value of 1.0 disables this setting. (default: 1)
     */
    @SerialName(value = "tfs_z")
    val tfsZ: Float? = null,

    /** Maximum number of tokens to predict when generating text. (Default: 128, -1 = infinite generation, -2 = fill context)  */
    @SerialName(value = "num_predict")
    val numPredict: Int? = null,

    /**
     * Reduces the probability of generating nonsense.
     * A higher value (e.g. 100) will give more diverse answers, while a lower value (e.g. 10) will be more conservative. (Default: 40)
     */
    @SerialName(value = "top_k")
    val topK: Int? = null,

    /**
     * Works together with top-k.
     * A higher value (e.g., 0.95) will lead to more diverse text, while a lower value (e.g., 0.5) will generate more focused and conservative text. (Default: 0.9)
     */
    @SerialName(value = "top_p")
    val topP: Float? = null,

    /**
     * Alternative to the top_p, and aims to ensure a balance of quality and variety.
     * The parameter p represents the minimum probability for a token to be considered, relative to the probability of the most likely token.
     * For example, with p=0.05 and the most likely token having a probability of 0.9, logits with a value less than 0.045 are filtered out. (Default: 0.0)
     */
    @SerialName(value = "min_p")
    val minP: Float? = null,

    /** Number of tokens to keep from the prompt.  */
    @SerialName(value = "num_keep")
    val numKeep: Int? = null,

    /** Typical p is used to reduce the impact of less probable tokens from the output.  */
    @SerialName(value = "typical_p")
    val typicalP: Float? = null,

    /** Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.  */
    @SerialName(value = "presence_penalty")
    val presencePenalty: Float? = null,

    /** Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.  */
    @SerialName(value = "frequency_penalty")
    val frequencyPenalty: Float? = null,

    /** Penalize newlines in the output. (Default: false)  */
    @SerialName(value = "penalize_newline")
    val penalizeNewline: Boolean? = null,

    /** Enable NUMA support. (Default: false)  */
    @SerialName(value = "numa")
    val numa: Boolean? = null,

    /** Sets the number of batches to use for generation. (Default: 1)  */
    @SerialName(value = "num_batch")
    val numBatch: Int? = null,

    /** The number of layers to send to the GPU(s). On macOS, it defaults to 1 to enable metal support, 0 to disable.  */
    @SerialName(value = "num_gpu")
    val numGpu: Int? = null,

    /** The GPU to use for the main model. Default is 0.  */
    @SerialName(value = "main_gpu")
    val mainGpu: Int? = null,

    /** Enable low VRAM mode. (Default: false)  */
    @SerialName(value = "low_vram")
    val lowVram: Boolean? = null,

    /** Enable f16 key/value. (Default: false)  */
    @SerialName(value = "f16_kv")
    val f16Kv: Boolean? = null,

    /** Enable logits all. (Default: false)  */
    @SerialName(value = "logits_all")
    val logitsAll: Boolean? = null,

    /** Enable vocab only. (Default: false)  */
    @SerialName(value = "vocab_only")
    val vocabOnly: Boolean? = null,

    /** Enable mmap. (Default: false)  */
    @SerialName(value = "use_mmap")
    val useMmap: Boolean? = null,

    /** Enable mlock. (Default: false)  */
    @SerialName(value = "use_mlock")
    val useMlock: Boolean? = null,

    /**
     * Sets the number of threads to use during computation.
     * By default, Ollama will detect this for optimal performance.
     * It is recommended to set this value to the number of physical CPU cores your system has (as opposed to the logical number of cores).  */
    @SerialName(value = "num_thread")
    val numThread: Int? = null,
)
