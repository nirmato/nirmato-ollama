package org.nirmato.ollama.api

import io.ktor.http.HttpStatusCode

/**
 * Represents an exception thrown when an invalid request is made to the Ollama API.
 */
public class InvalidRequestException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
