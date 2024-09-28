package org.jetbrains.kotlin.gradle.dsl

public fun KotlinCommonCompilerOptions.withCompilerArguments(configure: KotlinCommonCompilerOptionsBuilder.() -> Unit) {
    val arguments = KotlinCommonCompilerOptionsBuilder().apply(configure).build()

    freeCompilerArgs.addAll(arguments)
}
