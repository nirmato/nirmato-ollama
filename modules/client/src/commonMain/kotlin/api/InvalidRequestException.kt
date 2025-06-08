package org.nirmato.ollama.api

/**
 * Represents an exception thrown when an invalid request is made to the Ollama API.
 */
public class InvalidRequestException(
    statusCode: HttpStatusCode,
    error: FailureResponse,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
