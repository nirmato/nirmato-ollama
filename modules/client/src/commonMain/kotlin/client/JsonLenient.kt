package org.nirmato.ollama.client

import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json

/**
 * Internal Json Serializer.
 */
public val JsonLenient: Json = Json {
    isLenient = false
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
    classDiscriminatorMode = ClassDiscriminatorMode.NONE
}
