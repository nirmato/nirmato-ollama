package org.nirmato.ollama.annotations

import kotlin.RequiresOptIn.Level
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

@RequiresOptIn(
    message = "An API that is deprecated stage.",
    level = Level.WARNING,
)
@MustBeDocumented
@InternalApi
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class DeprecatedSince(
    val warningSince: Version,
    val errorSince: Version = Version.Unreleased,
    val hiddenSince: Version = Version.Unreleased,
)
