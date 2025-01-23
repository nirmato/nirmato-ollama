package org.jetbrains.kotlin.gradle.dsl

public fun KotlinJsCompilerOptions.withWasmJsCompilerArguments(configure: KotlinWasmJsCompilerOptionsBuilder.() -> Unit) {
    val arguments = KotlinWasmJsCompilerOptionsBuilder().apply(configure).build()

    freeCompilerArgs.addAll(arguments)
}
