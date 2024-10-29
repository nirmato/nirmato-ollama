package org.jetbrains.kotlin.gradle.dsl

public fun KotlinCommonCompilerOptions.withCommonCompilerArguments(configure: KotlinCommonCompilerOptionsBuilder.() -> Unit) {
    val arguments = KotlinCommonCompilerOptionsBuilder().apply(configure).build()

    freeCompilerArgs.addAll(arguments)
}
