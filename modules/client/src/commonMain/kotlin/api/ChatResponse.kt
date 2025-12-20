package org.nirmato.ollama.api

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The response class for the chat endpoint.
 */
@Serializable
public data class ChatResponse(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    val model: String? = null,

    /** Date on which a model was created. */
    @SerialName(value = "created_at")
    val createdAt: Instant? = null,

    @SerialName(value = "message")
    val message: Message? = null,

    /** Whether the response has completed. */
    @SerialName(value = "done")
    val done: Boolean? = null,

    @SerialName(value = "done_reason")
    val doneReason: DoneReason? = null,

    /** Time spent generating the response. */
    @SerialName(value = "total_duration")
    val totalDuration: Long? = null,

    /** Time spent in nanoseconds loading the model. */
    @SerialName(value = "load_duration")
    val loadDuration: Long? = null,

    /** Number of tokens in the prompt. */
    @SerialName(value = "prompt_eval_count")
    val promptEvalCount: Int? = null,

    /** Time spent in nanoseconds evaluating the prompt. */
    @SerialName(value = "prompt_eval_duration")
    val promptEvalDuration: Long? = null,

    /** Number of tokens the response. */
    @SerialName(value = "eval_count")
    val evalCount: Int? = null,

    /** Time in nanoseconds spent generating the response. */
    @SerialName(value = "eval_duration")
    val evalDuration: Long? = null,
)
