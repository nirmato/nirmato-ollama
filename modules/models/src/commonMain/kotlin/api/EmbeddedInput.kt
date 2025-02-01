package org.nirmato.ollama.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

@Serializable
public sealed interface EmbeddedInput {

    @JvmInline
    @Serializable
    public value class EmbeddedText(public val text: String) : EmbeddedInput

    @JvmInline
    @Serializable
    public value class EmbeddedList(public val texts: List<EmbeddedText>) : EmbeddedInput
}
