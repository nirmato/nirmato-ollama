package org.nirmato.ollama.api

/**
 * Represents an exception thrown when a permission error occurs while interacting with the Ollama API.
 */
public class PermissionException(
    statusCode: HttpStatusCode,
    error: FailureResponse,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
