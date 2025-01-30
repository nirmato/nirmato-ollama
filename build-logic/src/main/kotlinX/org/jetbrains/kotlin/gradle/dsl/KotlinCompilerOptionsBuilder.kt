package org.jetbrains.kotlin.gradle.dsl

public abstract class KotlinCompilerOptionsBuilder {
    protected val arguments: MutableList<String> = mutableListOf()

    public fun add(arg: String): Boolean = arguments.add(arg)
    public fun build(): List<String> = arguments
}
