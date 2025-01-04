package org.nirmato.ollama.api

import kotlin.jvm.JvmOverloads

/** An exception thrown in case of an I/O error */
public sealed class OllamaIOException(throwable: Throwable? = null) : OllamaException(message = throwable?.message, throwable = throwable)

/** An exception thrown in case a request times out. */
public class OllamaTimeoutException(throwable: Throwable? = null) : OllamaIOException(throwable = throwable)

/** An exception thrown in case of an I/O error */
public class GenericIOException(throwable: Throwable? = null) : OllamaIOException(throwable = throwable)
