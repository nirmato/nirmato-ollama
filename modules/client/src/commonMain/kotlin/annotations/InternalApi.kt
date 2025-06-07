package org.nirmato.ollama.annotations

import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

@RequiresOptIn(
    message = "An API for internal use only. It may be changed or deleted at any time and compatibility is not guaranteed.",
    level = RequiresOptIn.Level.ERROR,
)
@MustBeDocumented
@Retention(BINARY)
@Target(CLASS, PROPERTY, CONSTRUCTOR, FUNCTION, TYPEALIAS)
public annotation class InternalApi
