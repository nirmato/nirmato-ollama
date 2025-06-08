package org.nirmato.ollama.api

/**
 * Represents an exception thrown when an error occurs while interacting with the Ollama API.
 */
public sealed class ApiException(
    /** The HTTP status code associated with the error. */
    public val statusCode: HttpStatusCode,
    /** The error that occurred. */
    public val failure: FailureResponse,
    throwable: Throwable? = null,
) : OllamaException(message = failure.message, throwable = throwable)
