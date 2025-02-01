package org.nirmato.ollama.api

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import io.ktor.http.HttpStatusCode

@Serializable
public data class MonitoringResponse(

    @SerialName(value = "status")
    @Contextual
    public val status: HttpStatusCode,
)
