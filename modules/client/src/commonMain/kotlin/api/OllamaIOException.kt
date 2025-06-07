package org.nirmato.ollama.api

/** An exception thrown in case of an I/O error */
public sealed class OllamaIOException(throwable: Throwable? = null) : OllamaException(message = throwable?.message, throwable = throwable)

/** An exception thrown in case a request times out. */
public class OllamaTimeoutException(throwable: Throwable? = null) : OllamaIOException(throwable = throwable)

/** An exception thrown in case of an I/O error */
public class OllamaGenericIOException(throwable: Throwable? = null) : OllamaIOException(throwable = throwable)
