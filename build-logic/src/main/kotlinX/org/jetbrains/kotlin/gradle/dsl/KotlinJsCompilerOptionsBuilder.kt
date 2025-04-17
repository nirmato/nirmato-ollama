package org.jetbrains.kotlin.gradle.dsl

/**
 * Taken from https://github.com/JetBrains/kotlin/blob/master/compiler/arguments/src/org/jetbrains/kotlin/arguments/description/JsCompilerArguments.kt
 */
@Suppress("TooManyFunctions")
public class KotlinJsCompilerOptionsBuilder : KotlinCommonCompilerOptionsBuilder() {
    /** Keep IR. */
    public fun irKeep(): Boolean = arguments.add("-Xir-keep")

    /** Produce KLIB directory. */
    public fun irProduceKlibDir(): Boolean = arguments.add("-Xir-produce-klib-dir")

    /** Produce KLIB file. */
    public fun irProduceKlibFile(): Boolean = arguments.add("-Xir-produce-klib-file")

    /** Produce JS. */
    public fun irProduceJs(): Boolean = arguments.add("-Xir-produce-js")

    /** Enable IR DCE. */
    public fun irDce(): Boolean = arguments.add("-Xir-dce")

    /** Enable IR DCE runtime diagnostic. */
    public fun irDceRuntimeDiagnostic(): Boolean = arguments.add("-Xir-dce-runtime-diagnostic")

    /** Print IR DCE reachability info. */
    public fun irDcePrintReachabilityInfo(): Boolean = arguments.add("-Xir-dce-print-reachability-info")

    /** Enable IR property lazy initialization. */
    public fun irPropertyLazyInitialization(): Boolean = arguments.add("-Xir-property-lazy-initialization")

    /** Minimize IR member names. */
    public fun irMinimizedMemberNames(): Boolean = arguments.add("-Xir-minimized-member-names")

    /** Specify IR module name. */
    public fun irModuleName(name: String): Boolean = arguments.add("-Xir-module-name=$name")

    /** Enable safe external boolean in IR. */
    public fun irSafeExternalBoolean(): Boolean = arguments.add("-Xir-safe-external-boolean")

    /** Enable safe external boolean diagnostic in IR. */
    public fun irSafeExternalBooleanDiagnostic(): Boolean = arguments.add("-Xir-safe-external-boolean-diagnostic")

    /** Enable IR per module. */
    public fun irPerModule(): Boolean = arguments.add("-Xir-per-module")

    /** Specify IR per module output name. */
    public fun irPerModuleOutputName(name: String): Boolean = arguments.add("-Xir-per-module-output-name=$name")

    /** Enable IR per file. */
    public fun irPerFile(): Boolean = arguments.add("-Xir-per-file")

    /** Generate inline anonymous functions in IR. */
    public fun irGenerateInlineAnonymousFunctions(): Boolean = arguments.add("-Xir-generate-inline-anonymous-functions")

    /** Include files or directories. */
    public fun include(path: String): Boolean = arguments.add("-Xinclude=$path")

    /** Specify cache directory. */
    public fun cacheDirectory(directory: String): Boolean = arguments.add("-Xcache-directory=$directory")

    /** Enable IR build cache. */
    public fun irBuildCache(): Boolean = arguments.add("-Xir-build-cache")

    /** Generate DTS. */
    public fun generateDts(): Boolean = arguments.add("-Xgenerate-dts")

    /** Generate polyfills. */
    public fun generatePolyfills(): Boolean = arguments.add("-Xgenerate-polyfills")

    /** Enable strict implicit export types. */
    public fun strictImplicitExportTypes(): Boolean = arguments.add("-Xstrict-implicit-export-types")

    /** Enable ES classes. */
    public fun esClasses(): Boolean = arguments.add("-Xes-classes")

    /** Enable platform arguments in main function. */
    public fun platformArgumentsInMainFunction(): Boolean = arguments.add("-Xplatform-arguments-in-main-function")

    /** Enable ES generators. */
    public fun esGenerators(): Boolean = arguments.add("-Xes-generators")

    /** Enable ES arrow functions. */
    public fun esArrowFunctions(): Boolean = arguments.add("-Xes-arrow-functions")

    /** Enable typed arrays. */
    public fun typedArrays(): Boolean = arguments.add("-Xtyped-arrays")

    /** Disable friend modules. */
    public fun friendModulesDisabled(): Boolean = arguments.add("-Xfriend-modules-disabled")

    /** Specify friend modules. */
    public fun friendModules(modules: String): Boolean = arguments.add("-Xfriend-modules=$modules")

    /** Enable extension functions in externals. */
    public fun enableExtensionFunctionsInExternals(): Boolean = arguments.add("-Xenable-extension-functions-in-externals")

    /** Enable fake override validator. */
    public fun fakeOverrideValidator(): Boolean = arguments.add("-Xfake-override-validator")

    /** Optimize generated JS. */
    public fun optimizeGeneratedJs(): Boolean = arguments.add("-Xoptimize-generated-js")
}
