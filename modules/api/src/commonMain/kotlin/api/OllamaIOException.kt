package org.nirmato.ollama.api

import kotlin.jvm.JvmOverloads

/** An exception thrown in case of an I/O error */
public sealed class OllamaIOException @JvmOverloads constructor(throwable: Throwable? = null) : OllamaException(message = throwable?.message, throwable = throwable)

/** An exception thrown in case a request times out. */
public class OllamaTimeoutException @JvmOverloads constructor(throwable: Throwable? = null) : OllamaIOException(throwable = throwable)

/** An exception thrown in case of an I/O error */
public class GenericIOException @JvmOverloads constructor(throwable: Throwable? = null) : OllamaIOException(throwable = throwable)
