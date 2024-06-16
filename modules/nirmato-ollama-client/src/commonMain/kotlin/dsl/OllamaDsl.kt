package org.nirmato.ollama.dsl

/**
 * A marker annotations for DSLs.
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
internal annotation class OllamaDsl
