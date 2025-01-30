package org.jetbrains.kotlin.gradle.dsl

/**
 * Taken from https://github.com/JetBrains/kotlin/blob/master/compiler/cli/cli-common/src/org/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments.kt
 */
public open class KotlinCommonCompilerOptionsBuilder : KotlinCompilerOptionsBuilder() {
    /** Disable inlining. */
    public fun noInline(): Boolean = arguments.add("-Xno-inline")

    /** Skip metadata version check. */
    public fun skipMetadataVersionCheck(): Boolean = arguments.add("-Xskip-metadata-version-check")

    /** Skip prerelease check. */
    public fun skipPrereleaseCheck(): Boolean = arguments.add("-Xskip-prerelease-check")

    /** Allow Kotlin package. */
    public fun allowKotlinPackage(): Boolean = arguments.add("-Xallow-kotlin-package")

    /** Enable stdlib compilation. */
    public fun stdlibCompilation(): Boolean = arguments.add("-Xstdlib-compilation")

    /** Report output files. */
    public fun reportOutputFiles(): Boolean = arguments.add("-Xreport-output-files")

    /** Specify plugin. */
    public fun plugin(path: String): Boolean = arguments.add("-Xplugin=$path")

    /** Specify compiler plugin. */
    public fun compilerPlugin(path: String): Boolean = arguments.add("-Xcompiler-plugin=$path")

    /** Enable multi-platform. */
    public fun multiPlatform(): Boolean = arguments.add("-Xmulti-platform")

    /** Disable actual check. */
    public fun noCheckActual(): Boolean = arguments.add("-Xno-check-actual")

    /** Specify IntelliJ plugin root. */
    public fun intellijPluginRoot(path: String): Boolean = arguments.add("-Xintellij-plugin-root=$path")

    /** Enable new inference. */
    public fun newInference(): Boolean = arguments.add("-Xnew-inference")

    /** Enable inline classes. */
    public fun inlineClasses(): Boolean = arguments.add("-Xinline-classes")

    /** Enable legacy smart cast after try. */
    public fun legacySmartCastAfterTry(): Boolean = arguments.add("-Xlegacy-smart-cast-after-try")

    /** Report performance. */
    public fun reportPerf(): Boolean = arguments.add("-Xreport-perf")

    /** Dump performance data. */
    public fun dumpPerf(): Boolean = arguments.add("-Xdump-perf")

    /** Specify metadata version. */
    public fun metadataVersion(version: String): Boolean = arguments.add("-Xmetadata-version=$version")

    /** Specify common sources. */
    public fun commonSources(sources: String): Boolean = arguments.add("-Xcommon-sources=$sources")

    /** List phases. */
    public fun listPhases(): Boolean = arguments.add("-Xlist-phases")

    /** Disable phases. */
    public fun disablePhases(phases: String): Boolean = arguments.add("-Xdisable-phases=$phases")

    /** Enable verbose phases. */
    public fun verbosePhases(): Boolean = arguments.add("-Xverbose-phases")

    /** Specify phases to dump before. */
    public fun phasesToDumpBefore(phases: String): Boolean = arguments.add("-Xphases-to-dump-before=$phases")

    /** Specify phases to dump after. */
    public fun phasesToDumpAfter(phases: String): Boolean = arguments.add("-Xphases-to-dump-after=$phases")

    /** Specify phases to dump. */
    public fun phasesToDump(phases: String): Boolean = arguments.add("-Xphases-to-dump=$phases")

    /** Specify dump directory. */
    public fun dumpDirectory(directory: String): Boolean = arguments.add("-Xdump-directory=$directory")

    /** Specify dump fqname. */
    public fun dumpFqname(fqname: String): Boolean = arguments.add("-Xdump-fqname=$fqname")

    /** Specify phases to validate before. */
    public fun phasesToValidateBefore(phases: String): Boolean = arguments.add("-Xphases-to-validate-before=$phases")

    /** Specify phases to validate after. */
    public fun phasesToValidateAfter(phases: String): Boolean = arguments.add("-Xphases-to-validate-after=$phases")

    /** Specify phases to validate. */
    public fun phasesToValidate(phases: String): Boolean = arguments.add("-Xphases-to-validate=$phases")

    /** Enable IR verification. */
    public fun verifyIr(): Boolean = arguments.add("-Xverify-ir")

    /** Enable IR visibility verification. */
    public fun verifyIrVisibility(): Boolean = arguments.add("-Xverify-ir-visibility")

    /** Enable phase profiling. */
    public fun profilePhases(): Boolean = arguments.add("-Xprofile-phases")

    /** Check phase conditions. */
    public fun checkPhaseConditions(): Boolean = arguments.add("-Xcheck-phase-conditions")

    /** Use K2. */
    public fun useK2(): Boolean = arguments.add("-Xuse-k2")

    /** Use FIR experimental checkers. */
    public fun useFirExperimentalCheckers(): Boolean = arguments.add("-Xuse-fir-experimental-checkers")

    /** Use FIR incremental compilation. */
    public fun useFirIc(): Boolean = arguments.add("-Xuse-fir-ic")

    /** Use FIR light tree. */
    public fun useFirLt(): Boolean = arguments.add("-Xuse-fir-lt")

    /** Enable metadata KLIB. */
    public fun metadataKlib(): Boolean = arguments.add("-Xmetadata-klib")

    /** Disable default scripting plugin. */
    public fun disableDefaultScriptingPlugin(): Boolean = arguments.add("-Xdisable-default-scripting-plugin")

    /** Enable explicit API mode. */
    public fun explicitApi(): Boolean = arguments.add("-Xexplicit-api")

    /** Enable explicit return types. */
    public fun explicitReturnTypes(): Boolean = arguments.add("-XXexplicit-return-types")

    /** Enable inference compatibility. */
    public fun inferenceCompatibility(): Boolean = arguments.add("-Xinference-compatibility")

    /** Suppress version warnings. */
    public fun suppressVersionWarnings(): Boolean = arguments.add("-Xsuppress-version-warnings")

    /** Suppress API version greater than language version error. */
    public fun suppressApiVersionGreaterThanLanguageVersionError(): Boolean = arguments.add("-Xsuppress-api-version-greater-than-language-version-error")

    /** Enable extended compiler checks. */
    public fun extendedCompilerChecks(): Boolean = arguments.add("-Xextended-compiler-checks")

    /** Enable expect actual classes. */
    public fun expectActualClasses(): Boolean = arguments.add("-Xexpect-actual-classes")

    /** Enable consistent data class copy visibility. */
    public fun consistentDataClassCopyVisibility(): Boolean = arguments.add("-Xconsistent-data-class-copy-visibility")

    /** Enable unrestricted builder inference. */
    public fun unrestrictedBuilderInference(): Boolean = arguments.add("-Xunrestricted-builder-inference")

    /** Enable builder inference. */
    public fun enableBuilderInference(): Boolean = arguments.add("-Xenable-builder-inference")

    /** Enable self upper bound inference. */
    public fun selfUpperBoundInference(): Boolean = arguments.add("-Xself-upper-bound-inference")

    /** Enable context receivers. */
    public fun contextReceivers(): Boolean = arguments.add("-Xcontext-receivers")

    /** Enable context parameters. */
    public fun contextParameters(): Boolean = arguments.add("-Xcontext-parameters")

    /** Enable non-local break continue. */
    public fun nonLocalBreakContinue(): Boolean = arguments.add("-Xnon-local-break-continue")

    /** Enable direct Java actualization. */
    public fun directJavaActualization(): Boolean = arguments.add("-Xdirect-java-actualization")

    /** Enable multi-dollar interpolation. */
    public fun multiDollarInterpolation(): Boolean = arguments.add("-Xmulti-dollar-interpolation")

    /** Enable incremental compilation. */
    public fun enableIncrementalCompilation(): Boolean = arguments.add("-Xenable-incremental-compilation")

    /** Render internal diagnostic names. */
    public fun renderInternalDiagnosticNames(): Boolean = arguments.add("-Xrender-internal-diagnostic-names")

    /** Allow any scripts in source roots. */
    public fun allowAnyScriptsInSourceRoots(): Boolean = arguments.add("-Xallow-any-scripts-in-source-roots")

    /** Report all warnings. */
    public fun reportAllWarnings(): Boolean = arguments.add("-Xreport-all-warnings")

    /** Enable fragments. */
    public fun fragments(): Boolean = arguments.add("-Xfragments")

    /** Specify fragment sources. */
    public fun fragmentSources(sources: String): Boolean = arguments.add("-Xfragment-sources=$sources")

    /** Specify fragment refines. */
    public fun fragmentRefines(refines: String): Boolean = arguments.add("-Xfragment-refines=$refines")

    /** Ignore const optimization errors. */
    public fun ignoreConstOptimizationErrors(): Boolean = arguments.add("-Xignore-const-optimization-errors")

    /** Don't warn on error suppression. */
    public fun dontWarnOnErrorSuppression(): Boolean = arguments.add("-Xdont-warn-on-error-suppression")

    /** Enable when guards. */
    public fun whenGuards(): Boolean = arguments.add("-Xwhen-guards")

    /** Enable nested type aliases. */
    public fun nestedTypeAliases(): Boolean = arguments.add("-Xnested-type-aliases")

    /** Suppress warning. */
    public fun suppressWarning(warning: String): Boolean = arguments.add("-Xsuppress-warning=$warning")

    /** Specify annotation default target. */
    public fun annotationDefaultTarget(target: String): Boolean = arguments.add("-Xannotation-default-target=$target")

    /** Enable debug level compiler checks. */
    public fun debugLevelCompilerChecks(): Boolean = arguments.add("-XXdebug-level-compiler-checks")

    /** Enable annotation target all. */
    public fun annotationTargetAll(): Boolean = arguments.add("-Xannotation-target-all")
}
