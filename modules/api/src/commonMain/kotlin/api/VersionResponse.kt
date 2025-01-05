package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class VersionResponse(
    @SerialName(value = "version")
    public val version: String,
)
