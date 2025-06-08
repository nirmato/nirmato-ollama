package org.nirmato.ollama.client.ktor

import kotlinx.serialization.json.Json

/**
 * Configuration settings for [OllamaClient].
 *
 * @property jsonConfig JSON serialization configuration used for requests and responses.
 */
public class OllamaClientConfig(
    public val jsonConfig: Json = Json,
)
