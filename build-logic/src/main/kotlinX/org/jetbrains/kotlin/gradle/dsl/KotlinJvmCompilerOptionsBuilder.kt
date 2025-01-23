package org.jetbrains.kotlin.gradle.dsl

import org.gradle.api.JavaVersion

/**
 * Taken from https://github.com/JetBrains/kotlin/blob/master/compiler/cli/cli-common/src/org/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments.kt
 */
public class KotlinJvmCompilerOptionsBuilder : KotlinCommonCompilerOptionsBuilder() {
    public fun requiresJsr305(value: String = "strict"): Boolean = arguments.add("-Xjsr305=$value")
    public fun jvmDefault(jvmDefaultOption: JvmDefaultOption): Boolean = arguments.add("-Xjvm-default=${jvmDefaultOption.value}")
    public fun jdkRelease(jvmVersion: JavaVersion): Boolean = arguments.add("-Xjdk-release=$jvmVersion")

    public enum class JvmDefaultOption(public val value: String) {
        ALL("all"),
        ALL_COMPATIBILITY("all-compatibility"),
    }
}
