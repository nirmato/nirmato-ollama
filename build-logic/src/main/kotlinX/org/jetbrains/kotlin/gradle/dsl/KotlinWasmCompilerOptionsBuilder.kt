package org.jetbrains.kotlin.gradle.dsl

/**
 * Taken from https://github.com/JetBrains/kotlin/blob/master/compiler/arguments/src/org/jetbrains/kotlin/arguments/description/WasmCompilerArguments.kt
 */
public class KotlinWasmCompilerOptionsBuilder : KotlinCommonKlibCompilerOptionsBuilder() {
    /** Use the WebAssembly compiler backend. */
    public fun wasmCompilerBackend(): Boolean = arguments.add("-Xwasm")

    /** Set up the Wasm target (wasm-js or wasm-wasi). */
    public fun wasmTarget(target: WasmTarget): Boolean = arguments.add("-Xwasm-target=${target.target}")

    /** Add debug info to the compiled WebAssembly module. */
    public fun wasmDebugInfo(): Boolean = arguments.add("-Xwasm-debug-info")

    /** Avoid optimizations that can break debugging. */
    public fun wasmDebugFriendly(): Boolean = arguments.add("-Xwasm-debug-friendly")

    /** Generate a .wat file. */
    public fun wasmGenerateWat(): Boolean = arguments.add("-Xwasm-generate-wat")

    /** Enable support for 'KClass.qualifiedName'. */
    public fun wasmKClassFqn(): Boolean = arguments.add("-Xwasm-kclass-fqn")

    /** Turn on range checks for array access functions. */
    public fun wasmEnableArrayRangeChecks(): Boolean = arguments.add("-Xwasm-enable-array-range-checks")

    /** Turn on asserts. */
    public fun wasmEnableAsserts(): Boolean = arguments.add("-Xwasm-enable-asserts")

    /** Use traps instead of throwing exceptions. */
    public fun wasmUseTrapsInsteadOfExceptions(): Boolean = arguments.add("-Xwasm-use-traps-instead-of-exceptions")

    /** Use an updated version of the exception proposal with try_table. */
    public fun wasmUseNewExceptionProposal(): Boolean = arguments.add("-Xwasm-use-new-exception-proposal")

    /** Attach a thrown by JS-value to the JsException class. */
    public fun wasmAttachJsException(): Boolean = arguments.add("-Xwasm-attach-js-exception")

    /** Generates devtools custom formatters (https://firefox-source-docs.mozilla.org/devtools-user/custom_formatters) for Kotlin/Wasm values */
    public fun wasmDebuggerCustomFormatters(): Boolean = arguments.add("-Xwasm-debugger-custom-formatters")

    /** Insert source mappings from libraries even if their sources are unavailable on the end-user machine. */
    public fun wasmSourceMapIncludeMappingsFromUnavailableSources(): Boolean = arguments.add("-Xwasm-source-map-include-mappings-from-unavailable-sources")

    /** Preserve wasm file structure between IC runs. */
    public fun wasmPreserveIcOrder(): Boolean = arguments.add("-Xwasm-preserve-ic-order")

    /** Do not commit IC cache updates. */
    public fun wasmIcCacheReadonly(): Boolean = arguments.add("-Xwasm-ic-cache-readonly")

    /** Generate DWARF debug information. */
    public fun wasmGenerateDwarf(): Boolean = arguments.add("-Xwasm-generate-dwarf")

    /**
     * Dump reachability information collected about declarations while performing DCE to a file.
     * The format will be chosen automatically based on the file extension.
     * Supported output formats include JSON for .json, a JS const initialized with a plain object containing information for .js,
     * and plain text for all other file types.
     */
    public fun irDceDumpReachabilityInfoToFile(file: String): Boolean = arguments.add("-Xir-dce-dump-reachability-info-to-file=$file")

    /**
     * Dump the IR size of each declaration into a file.
     * The format will be chosen automatically depending on the file extension.
     * Supported output formats include JSON for .json, a JS const initialized with a plain object containing information for .js,
     * and plain text for all other file types.
     */
    public fun irDumpDeclarationIrSizesToFile(file: String): Boolean = arguments.add("-Xir-dump-declaration-ir-sizes-to-file=$file")

    public enum class WasmTarget(public val target: String) {
        WASM32("wasm-js"),
        WASM64("wasm-wasi")
    }
}
