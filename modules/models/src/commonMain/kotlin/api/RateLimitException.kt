package org.nirmato.ollama.api

import io.ktor.http.HttpStatusCode

/**
 * Represents an exception thrown when the Ollama API rate limit is exceeded.
 */
public class RateLimitException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
