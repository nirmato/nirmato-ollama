package org.jetbrains.kotlin.gradle.dsl

/**
 * Taken from https://github.com/JetBrains/kotlin/blob/master/compiler/cli/cli-common/src/org/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments.kt
 */
public class KotlinJvmCompilerOptionsBuilder : KotlinCommonCompilerOptionsBuilder() {
    /** Use the old JVM backend. */
    public fun useOldBackend(): Boolean = arguments.add("-Xuse-old-backend")

    /** Allow unstable dependencies. */
    public fun allowUnstableDependencies(): Boolean = arguments.add("-Xallow-unstable-dependencies")

    /** Specify ABI stability. */
    public fun abiStability(abiStability: AbiStability): Boolean = arguments.add("-Xabi-stability=${abiStability.value}")

    /** Do not clear the binding context in the IR backend. */
    public fun irDoNotClearBindingContext(): Boolean = arguments.add("-Xir-do-not-clear-binding-context")

    /** Specify the number of backend threads. */
    public fun backendThreads(threads: Int): Boolean = arguments.add("-Xbackend-threads=$threads")

    /** Specify the module path. */
    public fun modulePath(path: String): Boolean = arguments.add("-Xmodule-path=$path")

    /** Add modules. */
    public fun addModules(modules: String): Boolean = arguments.add("-Xadd-modules=$modules")

    /** Do not generate not-null assertions on method calls. */
    public fun noCallAssertions(): Boolean = arguments.add("-Xno-call-assertions")

    /** Do not generate not-null assertions on receiver expressions. */
    public fun noReceiverAssertions(): Boolean = arguments.add("-Xno-receiver-assertions")

    /** Do not generate not-null assertions on parameters of methods accessible from Java. */
    public fun noParamAssertions(): Boolean = arguments.add("-Xno-param-assertions")

    /** Disable optimizations. */
    public fun noOptimize(): Boolean = arguments.add("-Xno-optimize")

    /**
     * kotlin.assert call behavior:
     * -Xassertions=always-enable: enable, ignore JVM assertion settings;
     * -Xassertions=always-disable: disable, ignore JVM assertion settings;
     * -Xassertions=jvm: enable, depend on JVM assertion settings;
     * -Xassertions=legacy: calculate the condition on each call, the behavior depends on JVM assertion settings in the kotlin package;
     * default: legacy
     */
    public fun assertions(assertionType: AssertionType): Boolean = arguments.add("-Xassertions=${assertionType.value}")

    /** Specify the build file. */
    public fun buildFile(file: String): Boolean = arguments.add("-Xbuild-file=$file")

    /** Compile multi-file classes as a hierarchy of parts and a facade. */
    public fun multiFilePartsInherit(): Boolean = arguments.add("-Xmultifile-parts-inherit")

    /** Use a type table in metadata serialization. */
    public fun useTypeTable(): Boolean = arguments.add("-Xuse-type-table")

    /** Use old class files reading. */
    public fun useOldClassFilesReading(): Boolean = arguments.add("-Xuse-old-class-files-reading")

    /** Use fast jar file system. */
    public fun useFastJarFileSystem(): Boolean = arguments.add("-Xuse-fast-jar-file-system")

    /** Suppress missing builtins error. */
    public fun suppressMissingBuiltinsError(): Boolean = arguments.add("-Xsuppress-missing-builtins-error")

    /** Script resolver environment. */
    public fun scriptResolverEnvironment(env: String): Boolean = arguments.add("-Xscript-resolver-environment=$env")

    /** Use javac. */
    public fun useJavac(): Boolean = arguments.add("-Xuse-javac")

    /** Compile Java. */
    public fun compileJava(): Boolean = arguments.add("-Xcompile-java")

    /** Javac arguments. */
    public fun javacArguments(args: String): Boolean = arguments.add("-Xjavac-arguments=$args")

    /** Java source roots. */
    public fun javaSourceRoots(roots: String): Boolean = arguments.add("-Xjava-source-roots=$roots")

    /** Java package prefix. */
    public fun javaPackagePrefix(prefix: String): Boolean = arguments.add("-Xjava-package-prefix=$prefix")

    /** Specify the JSR-305 nullability annotations behavior. */
    public fun requiresJsr305(value: String = "strict"): Boolean = arguments.add("-Xjsr305=$value")

    /** Nullability annotations. */
    public fun nullabilityAnnotations(): Boolean = arguments.add("-Xnullability-annotations")

    /** Support compatqual checker framework annotations. */
    public fun supportCompatqualCheckerFrameworkAnnotations(supportCompatqualOption: SupportCompatqualOption): Boolean =
        arguments.add("-Xsupport-compatqual-checker-framework-annotations=${supportCompatqualOption.value}")

    /** JSpecify annotations. */
    public fun jspecifyAnnotations(jSpecifyAnnotationOption: JSpecifyAnnotationOption): Boolean = arguments.add("-Xjspecify-annotations=${jSpecifyAnnotationOption.value}")

    /** Specify the JVM default method generation strategy. */
    public fun jvmDefault(jvmDefaultOption: JvmDefaultOption): Boolean = arguments.add("-Xjvm-default=${jvmDefaultOption.value}")

    /** Default script extension. */
    public fun defaultScriptExtension(extension: String): Boolean = arguments.add("-Xdefault-script-extension=$extension")

    /** Disable standard script. */
    public fun disableStandardScript(): Boolean = arguments.add("-Xdisable-standard-script")

    /** Generate strict metadata version. */
    public fun generateStrictMetadataVersion(): Boolean = arguments.add("-Xgenerate-strict-metadata-version")

    /** Sanitize parentheses. */
    public fun sanitizeParentheses(): Boolean = arguments.add("-Xsanitize-parentheses")

    /** Specify friend paths. */
    public fun friendPaths(paths: String): Boolean = arguments.add("-Xfriend-paths=$paths")

    /** Allow no source files. */
    public fun allowNoSourceFiles(): Boolean = arguments.add("-Xallow-no-source-files")

    /** Emit JVM type annotations. */
    public fun emitJvmTypeAnnotations(): Boolean = arguments.add("-Xemit-jvm-type-annotations")

    /** Specify string concatenation strategy. */
    public fun stringConcat(strategy: String): Boolean = arguments.add("-Xstring-concat=$strategy")

    /** Specify the JDK release version. */
    public fun jdkRelease(version: String): Boolean = arguments.add("-Xjdk-release=$version")

    /** Enable SAM conversions. */
    public fun samConversions(): Boolean = arguments.add("-Xsam-conversions")

    /** Specify lambda generation strategy. */
    public fun lambdas(strategy: String): Boolean = arguments.add("-Xlambdas=$strategy")

    /** Specify klib path. */
    public fun klib(path: String): Boolean = arguments.add("-Xklib=$path")

    /** Do not reset jar timestamps. */
    public fun noResetJarTimestamps(): Boolean = arguments.add("-Xno-reset-jar-timestamps")

    /** Do not use unified null checks. */
    public fun noUnifiedNullChecks(): Boolean = arguments.add("-Xno-unified-null-checks")

    /** Do not include source debug extension. */
    public fun noSourceDebugExtension(): Boolean = arguments.add("-Xno-source-debug-extension")

    /** Enable profiling. */
    public fun profile(): Boolean = arguments.add("-Xprofile")

    /** Use 1.4 inline classes mangling scheme. */
    public fun use14InlineClassesManglingScheme(): Boolean = arguments.add("-Xuse-14-inline-classes-mangling-scheme")

    /** Enable JVM preview features. */
    public fun jvmEnablePreview(): Boolean = arguments.add("-Xjvm-enable-preview")

    /** Suppress deprecated JVM target warning. */
    public fun suppressDeprecatedJvmTargetWarning(): Boolean = arguments.add("-Xsuppress-deprecated-jvm-target-warning")

    /** Enable strict mode for type enhancement improvements. */
    public fun typeEnhancementImprovementsStrictMode(): Boolean = arguments.add("-Xtype-enhancement-improvements-strict-mode")

    /** Serialize IR. */
    public fun serializeIr(): Boolean = arguments.add("-Xserialize-ir")

    /** Validate bytecode. */
    public fun validateBytecode(): Boolean = arguments.add("-Xvalidate-bytecode")

    /** Enhance type parameter types to definitely not null. */
    public fun enhanceTypeParameterTypesToDefNotNull(): Boolean = arguments.add("-Xenhance-type-parameter-types-to-def-not-null")

    /** Link via signatures. */
    public fun linkViaSignatures(): Boolean = arguments.add("-Xlink-via-signatures")

    /** Enable debug mode. */
    public fun debug(): Boolean = arguments.add("-Xdebug")

    /** Do not use new Java annotation targets. */
    public fun noNewJavaAnnotationTargets(): Boolean = arguments.add("-Xno-new-java-annotation-targets")

    /** Use old inner classes logic. */
    public fun useOldInnerClassesLogic(): Boolean = arguments.add("-Xuse-old-innerclasses-logic")

    /** Enable value classes. */
    public fun valueClasses(): Boolean = arguments.add("-Xvalue-classes")

    /** Enable IR inliner. */
    public fun irInliner(): Boolean = arguments.add("-Xir-inliner")

    /** Use inline scopes numbers. */
    public fun useInlineScopesNumbers(): Boolean = arguments.add("-Xuse-inline-scopes-numbers")

    /** Use K2 KAPT. */
    public fun useK2Kapt(): Boolean = arguments.add("-Xuse-k2-kapt")

    /** Compile builtins as part of stdlib. */
    public fun compileBuiltinsAsPartOfStdlib(): Boolean = arguments.add("-Xcompile-builtins-as-part-of-stdlib")

    /** Output builtins metadata. */
    public fun outputBuiltinsMetadata(): Boolean = arguments.add("-Xoutput-builtins-metadata")

    public enum class AbiStability(public val value: String) {
        STABLE("stable"),
        EXPERIMENTAL("unstable"),
    }

    public enum class AssertionType(public val value: String) {
        ALWAYS_ENABLE("always-enable"),
        ALWAYS_DISABLE("always-disable"),
        JVM("jvm"),
        LEGACY("legacy"),
    }

    public enum class JvmDefaultOption(public val value: String) {
        ALL("all"),
        ALL_COMPATIBILITY("all-compatibility"),
    }

    public enum class SupportCompatqualOption(public val value: String) {
        ENABLE("enable"),
        DISABLE("disable"),
    }

    public enum class JSpecifyAnnotationOption(public val value: String) {
        IGNORE("ignore"),
        STRICT("strict"),
        WARN("warn"),
    }
}
