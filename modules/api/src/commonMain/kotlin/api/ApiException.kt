package org.nirmato.ollama.api

import io.ktor.http.HttpStatusCode

/**
 * Represents an exception thrown when an error occurs while interacting with the Ollama API.
 */
public sealed class ApiException(
    /** The HTTP status code associated with the error. */
    public val statusCode: HttpStatusCode,
    /** The error that occurred. */
    public val failure: ResponseFailure,
    throwable: Throwable? = null,
) : OllamaException(message = failure.message, throwable = throwable)

/**
 * Represents an exception thrown when the Ollama API rate limit is exceeded.
 */
public class RateLimitException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when an invalid request is made to the Ollama API.
 */
public class InvalidRequestException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when an authentication error occurs while interacting with the Ollama API.
 */
public class AuthenticationException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when a permission error occurs while interacting with the Ollama API.
 */
public class PermissionException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when an unknown error occurs while interacting with the Ollama API.
 * This exception is used when the specific type of error is not covered by the existing subclasses.
 */
public class UnknownAPIException(
    statusCode: HttpStatusCode,
    error: ResponseFailure,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
