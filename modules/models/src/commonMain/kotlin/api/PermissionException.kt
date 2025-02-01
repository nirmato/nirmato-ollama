package org.nirmato.ollama.api

import io.ktor.http.HttpStatusCode

/**
 * Represents an exception thrown when a permission error occurs while interacting with the Ollama API.
 */
public class PermissionException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
