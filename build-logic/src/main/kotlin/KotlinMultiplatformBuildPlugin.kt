package build.gradle.plugins.build

import build.gradle.plugins.build.KarmaBrowserTarget.Chrome
import build.gradle.plugins.build.KarmaBrowserTarget.ChromeCanary
import build.gradle.plugins.build.KarmaBrowserTarget.ChromeCanaryHeadless
import build.gradle.plugins.build.KarmaBrowserTarget.ChromeHeadless
import build.gradle.plugins.build.KarmaBrowserTarget.Chromium
import build.gradle.plugins.build.KarmaBrowserTarget.ChromiumHeadless
import build.gradle.plugins.build.KarmaBrowserTarget.Firefox
import build.gradle.plugins.build.KarmaBrowserTarget.FirefoxAurora
import build.gradle.plugins.build.KarmaBrowserTarget.FirefoxAuroraHeadless
import build.gradle.plugins.build.KarmaBrowserTarget.FirefoxDeveloper
import build.gradle.plugins.build.KarmaBrowserTarget.FirefoxDeveloperHeadless
import build.gradle.plugins.build.KarmaBrowserTarget.FirefoxHeadless
import build.gradle.plugins.build.KarmaBrowserTarget.FirefoxNightly
import build.gradle.plugins.build.KarmaBrowserTarget.FirefoxNightlyHeadless
import build.gradle.plugins.build.KarmaBrowserTarget.Ie
import build.gradle.plugins.build.KarmaBrowserTarget.Opera
import build.gradle.plugins.build.KarmaBrowserTarget.PhantomJs
import build.gradle.plugins.build.KarmaBrowserTarget.Safari
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.attributes.java.TargetJvmVersion
import org.gradle.api.getProperty
import org.gradle.api.gradleBooleanProperty
import org.gradle.api.gradleStringProperty
import org.gradle.api.tasks.StopExecutionException
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JsSourceMapEmbedMode
import org.jetbrains.kotlin.gradle.dsl.JsSourceMapNamesPolicy
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptionsBuilder.JvmDefaultOption
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.withCommonCompilerArguments
import org.jetbrains.kotlin.gradle.dsl.withJvmCompilerArguments
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

public class KotlinMultiplatformBuildPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        apply<KotlinMultiplatformPluginWrapper>()

        configureJvmToolchain()
        configureKotlinSourceSets()

        configureAllTargets()
        configureTargets()
    }

    private fun Project.configureTargets() {
        val hostOs = System.getProperty("os.name")
        val isWindows = hostOs.startsWith("Windows")
        val isLinux = hostOs.startsWith("Linux")
        val isMacos = hostOs.startsWith("Mac OS X")

        configureJvmTarget()
        configureJsTarget()
        configureWasmJsTarget()

        if (isLinux) {
            configureLinuxTargets()
        }

        if (isWindows) {
            configureWindowsTargets()
        }

        if (isMacos) {
            configureMacosTargets()
            configureTvosTargets()
            configureWatchosTargets()
            configureIosTargets()
        }
    }

    private fun Project.configureWindowsTargets() {
        val windowsTargetEnabled = project.gradleBooleanProperty("kotlin.targets.windows.enabled").get()
        if (windowsTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                mingwX64()
            }

            checkWindowsTasks()
        }
    }

    private fun Project.configureLinuxTargets() {
        val linuxTargetEnabled = project.gradleBooleanProperty("kotlin.targets.linux.enabled").get()
        if (linuxTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                linuxX64()
                linuxArm64()
            }

            checkLinuxTasks()
        }
    }

    private fun Project.configureMacosTargets() {
        val macosTargetEnabled = project.gradleBooleanProperty("kotlin.targets.macos.enabled").get()
        if (macosTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                macosX64()
                macosArm64()
            }

            checkMacosTasks()
        }
    }

    private fun Project.configureTvosTargets() {
        val tvosTargetEnabled = project.gradleBooleanProperty("kotlin.targets.tvos.enabled").get()
        if (tvosTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                tvosX64()
                tvosArm64()
                tvosSimulatorArm64()
            }

            checkTvosTasks()
        }
    }

    private fun Project.configureWatchosTargets() {
        val watchosTargetEnabled = project.gradleBooleanProperty("kotlin.targets.watchos.enabled").get()
        if (watchosTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                watchosX64()
                watchosArm64()
                watchosDeviceArm64()
                watchosSimulatorArm64()
            }

            checkWatchosTasks()
        }
    }

    private fun Project.configureIosTargets() {
        val iosTargetEnabled = project.gradleBooleanProperty("kotlin.targets.ios.enabled").get()
        if (iosTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                iosX64()
                iosArm64()
                iosSimulatorArm64()
            }

            checkIosTasks()
        }
    }

    private fun Project.configureJvmToolchain() {
        configure<KotlinMultiplatformExtension> {
            jvmToolchain {
                languageVersion = project.providers.provider {
                    JavaLanguageVersion.of(
                        project.getProperty("kotlin.javaToolchain.mainJvmCompiler")
                            ?: throw StopExecutionException("Property \"kotlin.javaToolchain.mainJvmCompiler\" is not found")
                    )
                }
            }
        }
    }

    private fun Project.checkJsTasks() {
        val jsTest = tasks.named("jsTest")

        tasks.register("checkJs") {
            group = "verification"
            description = "Runs all checks for the Kotlin/JS platform."
            dependsOn(jsTest)
        }
    }

    private fun Project.checkWasmJsTasks() {
        val wasmJsTest = tasks.named("wasmJsTest")

        tasks.register("checkWasmJs") {
            group = "verification"
            description = "Runs all checks for the Kotlin/WasmJS platform."
            dependsOn(wasmJsTest)
        }
    }

    private fun Project.checkJvmTasks() {
        val jvmTest = tasks.named("jvmTest")

        tasks.register("checkJvm") {
            group = "verification"
            description = "Runs all checks for the Kotlin/JVM platform."
            dependsOn(jvmTest)
            dependsOnJvmApiCheck(project)
        }
    }

    private fun Project.checkWindowsTasks() {
        val mingwX64Test = tasks.named("mingwX64Test")

        tasks.register("checkWindows") {
            group = "verification"
            description = "Runs all checks for the Kotlin/Multiplatform for Windows."
            dependsOn(mingwX64Test)
        }
    }

    private fun Project.checkLinuxTasks() {
        val linuxX64Test = tasks.named("linuxX64Test")

        tasks.register("checkLinux") {
            group = "verification"
            description = "Runs all checks for the Kotlin/Multiplatform for Linux."
            dependsOn(linuxX64Test)
        }
    }

    private fun Project.checkMacosTasks() {
        val macosX64Test = tasks.named("macosX64Test")

        tasks.register("checkMacos") {
            group = "verification"
            description = "Runs all checks for the Kotlin/Multiplatform for MacOS."
            dependsOn(macosX64Test)
        }
    }

    private fun Project.checkTvosTasks() {
        val tvosX64Test = tasks.named("tvosX64Test")

        tasks.register("checkTvos") {
            group = "verification"
            description = "Runs all checks for the Kotlin/Multiplatform for TvOS."
            dependsOn(tvosX64Test)
        }
    }

    private fun Project.checkWatchosTasks() {
        val watchosX64Test = tasks.named("watchosX64Test")

        tasks.register("checkWatchos") {
            group = "verification"
            description = "Runs all checks for the Kotlin/Multiplatform for WatchOS."
            dependsOn(watchosX64Test)
        }
    }

    private fun Project.checkIosTasks() {
        val iosX64Test = tasks.named("iosX64Test")

        tasks.register("checkIos") {
            group = "verification"
            description = "Runs all checks for the Kotlin/Multiplatform for IOS."
            dependsOn(iosX64Test)
        }
    }

    private fun Task.dependsOnJvmApiCheck(project: Project) {
        if (project.plugins.hasPlugin("org.jetbrains.kotlinx.binary-compatibility-validator")) {
            val jvmApiCheck = project.tasks.named("jvmApiCheck")
            dependsOn(jvmApiCheck)
        }
    }

    private fun Project.configureKotlinSourceSets() {
        configure<KotlinMultiplatformExtension> {
            sourceSets.configureEach {
                languageSettings.apply {
                    apiVersion = project.gradleStringProperty("kotlin.compilerOptions.apiVersion").get()
                    languageVersion = project.gradleStringProperty("kotlin.compilerOptions.languageVersion").get()
                    progressiveMode = true
                }
            }
        }
    }

    private fun Project.configureAllTargets() {
        configure<KotlinMultiplatformExtension> {
            targets.configureEach {
                compilations.configureEach {
                    compileTaskProvider.configure {
                        compilerOptions {
                            apiVersion = providers.gradleProperty("kotlin.compilerOptions.apiVersion").map(KotlinVersion::fromVersion)
                            languageVersion = providers.gradleProperty("kotlin.compilerOptions.languageVersion").map(KotlinVersion::fromVersion)
                            progressiveMode = true

                            withCommonCompilerArguments {
                                requiresOptIn()
                                suppressExpectActualClasses()
                                suppressVersionWarnings()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun Project.configureJsTarget() {
        val jsTargetEnabled = project.gradleBooleanProperty("kotlin.targets.js.enabled").get()
        if (jsTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                js {
                    moduleName = project.name + "-js"

                    compilations.configureEach {
                        compileTaskProvider.configure {
                            compilerOptions {
                                sourceMap = true
                                sourceMapEmbedSources = JsSourceMapEmbedMode.SOURCE_MAP_SOURCE_CONTENT_ALWAYS
                                sourceMapNamesPolicy = JsSourceMapNamesPolicy.SOURCE_MAP_NAMES_POLICY_FQ_NAMES
                            }
                        }
                    }

                    browser {
                        testTask {
                            useKarma {
                                when (karmaBrowserTarget()) {
                                    Chrome -> useChrome()
                                    ChromeHeadless -> useChromeHeadless()
                                    ChromeCanary -> useChromeCanary()
                                    ChromeCanaryHeadless -> useChromeCanaryHeadless()
                                    Chromium -> useChromium()
                                    ChromiumHeadless -> useChromiumHeadless()
                                    Firefox -> useFirefox()
                                    FirefoxHeadless -> useFirefoxHeadless()
                                    FirefoxAurora -> useFirefoxAurora()
                                    FirefoxAuroraHeadless -> useFirefoxAuroraHeadless()
                                    FirefoxDeveloper -> useFirefoxDeveloper()
                                    FirefoxDeveloperHeadless -> useFirefoxDeveloperHeadless()
                                    FirefoxNightly -> useFirefoxNightly()
                                    FirefoxNightlyHeadless -> useFirefoxNightlyHeadless()
                                    PhantomJs -> usePhantomJS()
                                    Safari -> useSafari()
                                    Opera -> useOpera()
                                    Ie -> useIe()
                                }
                            }
                        }
                    }

                    nodejs {
                        testTask {
                            useMocha {
                                timeout = "60s"
                            }
                        }
                    }

                    binaries.executable()
                    binaries.library()
                }
            }

            rootProject.plugins.withType<YarnPlugin> {
                yarn.apply {
                    lockFileDirectory = rootDir.resolve("gradle/js")
                    reportNewYarnLock = true
                    yarnLockAutoReplace = true
                    yarnLockMismatchReport = YarnLockMismatchReport.FAIL

                    resolution("braces", "3.0.3")
                    resolution("follow-redirects", "1.15.6")
                    resolution("body-parser", "1.20.3")
                    resolution("http-proxy-middleware", "2.0.7")
                }
            }

            rootProject.the<YarnRootEnvSpec>().apply {
                download = false
                ignoreScripts = false
            }

            rootProject.the<NodeJsEnvSpec>().apply {
                download = false
            }

            applyKotlinJsImplicitDependencyWorkaround()

            checkJsTasks()
        }
    }

    private fun Project.karmaBrowserTarget(): KarmaBrowserTarget {
        val browser = KarmaBrowser.Firefox
        val channel = KarmaBrowserChannel.Release
        val headless = true

        return KarmaBrowserTarget.valueOf(browser, channel, headless = headless)
    }

    @OptIn(ExperimentalWasmDsl::class)
    private fun Project.configureWasmJsTarget() {
        val wasmJsTargetEnabled = project.gradleBooleanProperty("kotlin.targets.wasmJs.enabled").get()
        if (wasmJsTargetEnabled) {
            configure<KotlinMultiplatformExtension> {
                wasmJs {
                    moduleName = project.name + "-wasm"

                    compilations.configureEach {
                        compileTaskProvider.configure {
                            compilerOptions {
                                sourceMap = true
                                sourceMapEmbedSources = JsSourceMapEmbedMode.SOURCE_MAP_SOURCE_CONTENT_ALWAYS
                                sourceMapNamesPolicy = JsSourceMapNamesPolicy.SOURCE_MAP_NAMES_POLICY_FQ_NAMES
                            }
                        }
                    }

                    browser()
                    nodejs {
                        testTask {
                            useMocha {
                                timeout = "60s"
                            }
                        }
                    }

                    binaries.executable()
                    binaries.library()
                }
            }

            applyKotlinWasmJsImplicitDependencyWorkaround()

            checkWasmJsTasks()
        }
    }

    // https://youtrack.jetbrains.com/issue/KT-56025
    private fun Project.applyKotlinJsImplicitDependencyWorkaround() {
        val compileSyncTasks = getKotlinJsCompileSyncTasks()

        tasks.named("jsBrowserProductionWebpack").configure(compileSyncTasks)
        tasks.named("jsBrowserProductionLibraryDistribution").configure(compileSyncTasks)
        tasks.named("jsNodeProductionLibraryDistribution").configure(compileSyncTasks)

        if (project.gradleBooleanProperty("kotlin.targets.wasmJs.enabled").get()) {
            tasks.named("jsNodeTest").configure {
                dependsOn(tasks.getByPath("wasmJsTestTestDevelopmentExecutableCompileSync"))
            }

            tasks.named("jsBrowserTest").configure {
                dependsOn(tasks.getByPath("wasmJsTestTestDevelopmentExecutableCompileSync"))
            }
        }
    }

    // https://youtrack.jetbrains.com/issue/KT-56025
    private fun Project.applyKotlinWasmJsImplicitDependencyWorkaround() {
        val compileSyncTasks = getKotlinWasmCompileSyncTasks()

        tasks.named("wasmJsBrowserProductionWebpack").configure(compileSyncTasks)
        tasks.named("wasmJsBrowserProductionLibraryDistribution").configure(compileSyncTasks)
        tasks.named("wasmJsBrowserDistribution").configure(compileSyncTasks)
        tasks.named("wasmJsNodeProductionLibraryDistribution").configure(compileSyncTasks)

        if (project.gradleBooleanProperty("kotlin.targets.js.enabled").get()) {
            tasks.named("wasmJsBrowserTest").configure {
                dependsOn(tasks.getByPath("jsTestTestDevelopmentExecutableCompileSync"))
            }

            tasks.named("wasmJsNodeTest").configure {
                dependsOn(tasks.getByPath("jsTestTestDevelopmentExecutableCompileSync"))
            }
        }
    }

    private fun Project.getKotlinJsCompileSyncTasks(): Task.() -> Unit = {
        dependsOn(tasks.getByPath("jsProductionLibraryCompileSync"))
        dependsOn(tasks.getByPath("jsProductionExecutableCompileSync"))
    }

    private fun Project.getKotlinWasmCompileSyncTasks(): Task.() -> Unit = {
        dependsOn(tasks.getByPath("wasmJsProductionLibraryCompileSync"))
        dependsOn(tasks.getByPath("wasmJsProductionExecutableCompileSync"))
    }

    private fun Project.configureJvmTarget() {
        val jvmTargetEnabled = project.gradleBooleanProperty("kotlin.targets.jvm.enabled").get()
        if (jvmTargetEnabled) {
            val jvmVersion = project.gradleStringProperty("kotlin.targets.jvm.target").map(JavaVersion::toVersion).get()

            configure<KotlinMultiplatformExtension> {
                jvm {
                    compilations.configureEach {
                        compileTaskProvider.configure {
                            compilerOptions {
                                withJvmCompilerArguments {
                                    requiresJsr305()
                                    jvmDefault(JvmDefaultOption.ALL_COMPATIBILITY)
                                }
                                this.javaParameters = true
                                this.jvmTarget = JvmTarget.fromTarget(jvmVersion.majorVersion)
                            }
                        }
                    }

                    attributes {
                        attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, JavaVersion.toVersion(jvmVersion).majorVersion.toInt())
                    }
                }
            }

            checkJvmTasks()
        }
    }

    public companion object {
        public const val PLUGIN_ID: String = "build-kotlin-multiplatform"
    }
}
