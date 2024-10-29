package org.jetbrains.kotlin.gradle.dsl

public fun KotlinJvmCompilerOptions.withJvmCompilerArguments(configure: KotlinJvmCompilerOptionsBuilder.() -> Unit) {
    val arguments = KotlinJvmCompilerOptionsBuilder().apply(configure).build()

    freeCompilerArgs.addAll(arguments)
}
