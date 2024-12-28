package org.nirmato.ollama.annotations

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

@InternalApi
@MustBeDocumented
@Retention(SOURCE)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class Since(val version: Version)
