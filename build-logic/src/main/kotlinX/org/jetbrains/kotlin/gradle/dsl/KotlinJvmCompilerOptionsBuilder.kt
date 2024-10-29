package org.jetbrains.kotlin.gradle.dsl

public class KotlinJvmCompilerOptionsBuilder: KotlinCommonCompilerOptionsBuilder() {
    public fun requiresJsr305(value: String = "strict"): Boolean = arguments.add("-Xjsr305=$value")
    public fun jvmDefault(jvmDefaultOption: JvmDefaultOption): Boolean = arguments.add("-Xjvm-default=${jvmDefaultOption.value}")

    public enum class JvmDefaultOption(public val value: String) {
        ALL("all"),
        ALL_COMPATIBILITY("all-compatibility"),
    }
}
