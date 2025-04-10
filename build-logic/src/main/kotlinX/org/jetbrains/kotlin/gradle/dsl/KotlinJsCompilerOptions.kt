package org.jetbrains.kotlin.gradle.dsl

public fun KotlinJsCompilerOptions.withJsCompilerArguments(configure: KotlinJsCompilerOptionsBuilder.() -> Unit) {
    val arguments = KotlinJsCompilerOptionsBuilder().apply(configure).build()

    freeCompilerArgs.addAll(arguments)
}

public fun KotlinJsCompilerOptions.withWasmCompilerArguments(configure: KotlinWasmCompilerOptionsBuilder.() -> Unit) {
    val arguments = KotlinWasmCompilerOptionsBuilder().apply(configure).build()

    freeCompilerArgs.addAll(arguments)
}
