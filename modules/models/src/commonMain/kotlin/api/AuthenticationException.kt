package org.nirmato.ollama.api

import io.ktor.http.HttpStatusCode

/**
 * Represents an exception thrown when an authentication error occurs while interacting with the Ollama API.
 */
public class AuthenticationException(
    statusCode: HttpStatusCode,
    error: FailureResponse,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
