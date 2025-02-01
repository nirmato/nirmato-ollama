package org.nirmato.ollama.api

import io.ktor.http.HttpStatusCode

/**
 * Represents an exception thrown when an unknown error occurs while interacting with the Ollama API.
 * This exception is used when the specific type of error is not covered by the existing subclasses.
 */
public class UnknownAPIException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
