package org.nirmato.ollama.api

import kotlin.jvm.JvmOverloads
import io.ktor.http.HttpStatusCode

/**
 * Represents an exception thrown when an error occurs while interacting with the Ollama API.
 */
public sealed class ApiException @JvmOverloads constructor(
    /** The HTTP status code associated with the error. */
    public val statusCode: HttpStatusCode,
    /** The error that occurred. */
    public val error: OllamaError,
    throwable: Throwable? = null,
) : OllamaException(message = error.detail?.message, throwable = throwable)

/**
 * Represents an exception thrown when the Ollama API rate limit is exceeded.
 */
public class RateLimitException @JvmOverloads constructor(
    statusCode: HttpStatusCode,
    error: OllamaError,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when an invalid request is made to the Ollama API.
 */
public class InvalidRequestException @JvmOverloads constructor(
    statusCode: HttpStatusCode,
    error: OllamaError,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when an authentication error occurs while interacting with the Ollama API.
 */
public class AuthenticationException @JvmOverloads constructor(
    statusCode: HttpStatusCode,
    error: OllamaError,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when a permission error occurs while interacting with the Ollama API.
 */
public class PermissionException @JvmOverloads constructor(
    statusCode: HttpStatusCode,
    error: OllamaError,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)

/**
 * Represents an exception thrown when an unknown error occurs while interacting with the Ollama API.
 * This exception is used when the specific type of error is not covered by the existing subclasses.
 */
public class UnknownAPIException @JvmOverloads constructor(
    statusCode: HttpStatusCode,
    error: OllamaError,
    throwable: Throwable? = null,
) : ApiException(statusCode, error, throwable)
