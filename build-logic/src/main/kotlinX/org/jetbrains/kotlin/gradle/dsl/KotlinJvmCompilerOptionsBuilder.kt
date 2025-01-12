package org.jetbrains.kotlin.gradle.dsl

import org.gradle.api.JavaVersion

public class KotlinJvmCompilerOptionsBuilder: KotlinCommonCompilerOptionsBuilder() {
    public fun requiresJsr305(value: String = "strict"): Boolean = arguments.add("-Xjsr305=$value")
    public fun jvmDefault(jvmDefaultOption: JvmDefaultOption): Boolean = arguments.add("-Xjvm-default=${jvmDefaultOption.value}")
    public fun jdkRelease(jvmVersion: JavaVersion): Boolean = arguments.add("-Xjdk-release=$jvmVersion")

    public enum class JvmDefaultOption(public val value: String) {
        ALL("all"),
        ALL_COMPATIBILITY("all-compatibility"),
    }
}
