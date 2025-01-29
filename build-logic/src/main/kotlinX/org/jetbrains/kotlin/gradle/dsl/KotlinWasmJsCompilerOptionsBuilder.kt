package org.jetbrains.kotlin.gradle.dsl

/**
 * Taken from https://github.com/JetBrains/kotlin/blob/master/compiler/cli/cli-common/src/org/jetbrains/kotlin/cli/common/arguments/K2WasmCompilerArguments.kt
 */
public class KotlinWasmJsCompilerOptionsBuilder : KotlinCommonCompilerOptionsBuilder(){
    public fun wasmDebugInfo(): Boolean = arguments.add("-Xwasm-debug-info")
    public fun wasmDebugFriendly(): Boolean = arguments.add("-Xwasm-debug-friendly")
    public fun wasmGenerateWat(): Boolean = arguments.add("-Xwasm-generate-wat")
    public fun wasmGenerateDwarf(): Boolean = arguments.add("-Xwasm-generate-dwarf")
    public fun wasmDebuggerCustomFormatters(): Boolean = arguments.add("-Xwasm-debugger-custom-formatters")
}
