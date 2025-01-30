package org.jetbrains.kotlin.gradle.dsl

/**
 * Taken from https://github.com/JetBrains/kotlin/blob/master/compiler/cli/cli-common/src/org/jetbrains/kotlin/cli/common/arguments/CommonKlibBasedCompilerArguments.kt
 */
public open class KotlinCommonKlibCompilerOptionsBuilder : KotlinCommonCompilerOptionsBuilder() {
    /** Specify KLIB relative path base. */
    public fun klibRelativePathBase(path: String): Boolean = arguments.add("-Xklib-relative-path-base=$path")
    /** Normalize absolute paths in KLIB. */
    public fun klibNormalizeAbsolutePath(): Boolean = arguments.add("-Xklib-normalize-absolute-path")
    /** Enable signature clash checks in KLIB. */
    public fun klibEnableSignatureClashChecks(): Boolean = arguments.add("-Xklib-enable-signature-clash-checks")
    /** Enable partial linkage. */
    public fun partialLinkage(): Boolean = arguments.add("-Xpartial-linkage")
    /** Specify log level for partial linkage. */
    public fun partialLinkageLogLevel(level: String): Boolean = arguments.add("-Xpartial-linkage-loglevel=$level")
    /** Specify strategy for duplicated unique names in KLIB. */
    public fun klibDuplicatedUniqueNameStrategy(strategy: String): Boolean = arguments.add("-Xklib-duplicated-unique-name-strategy=$strategy")
    /** Enable IR inliner in KLIB. */
    public fun klibIrInliner(): Boolean = arguments.add("-Xklib-ir-inliner")
}
