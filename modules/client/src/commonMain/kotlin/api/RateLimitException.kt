package org.nirmato.ollama.api

/**
 * Represents an exception thrown when the Ollama API rate limit is exceeded.
 */
public class RateLimitException(
    statusCode: HttpStatusCode,
    error: FailureResponse,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
