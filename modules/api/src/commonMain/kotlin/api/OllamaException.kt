package org.nirmato.ollama.api

import kotlin.jvm.JvmOverloads

/** Ollama client exception */
public sealed class OllamaException @JvmOverloads constructor(message: String? = null, throwable: Throwable? = null) : RuntimeException(message, throwable)

/** Runtime Http Client exception */
public class OllamaClientException @JvmOverloads constructor(throwable: Throwable? = null) : OllamaException(throwable?.message, throwable)

/** An exception thrown in case of a server error */
public class OllamaServerException @JvmOverloads constructor(throwable: Throwable? = null) : OllamaException(throwable?.message, throwable)
