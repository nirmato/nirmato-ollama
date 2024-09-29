package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.getVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.withCompilerArguments
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

public class MultiplatformPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.apply<KotlinMultiplatformPluginWrapper>()

        project.configureJvmToolchain()

        project.configureAllTargets()
        project.configureJvmTarget(JvmTarget.JVM_17)
        project.configureJsTarget()
        project.configureWasmJsTarget()

        project.configureKotlinSourceSets(
            apiVersion = "1.7",
            languageVersion = "2.0",
        )
    }

    private fun Project.configureJvmToolchain() {
        configure<KotlinMultiplatformExtension> {
            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(project.getVersion("jvm-toolchain")))
            }
        }
    }

    private fun Project.checkJsTask() {
        val jsTest = tasks.named("jsTest")

        tasks.register("checkJs") {
            group = "verification"
            description = "Runs all checks for the Kotlin/JS platform."
            dependsOn(jsTest)
        }
    }

    private fun Project.checkWasmJsTask() {
        val wasmJsTest = tasks.named("wasmJsTest")

        tasks.register("checkWasmJs") {
            group = "verification"
            description = "Runs all checks for the Kotlin/WasmJS platform."
            dependsOn(wasmJsTest)
        }
    }

    private fun Project.checkJvmTask() {
        val jvmTest = tasks.named("jvmTest")

        tasks.register("checkJvm") {
            group = "verification"
            description = "Runs all checks for the Kotlin/JVM platform."
            dependsOn(jvmTest)
            dependsOnJvmApiCheck(project)
        }
    }

    private fun Task.dependsOnJvmApiCheck(project: Project) {
        if (project.plugins.hasPlugin("org.jetbrains.kotlinx.binary-compatibility-validator")) {
            val jvmApiCheck = project.tasks.named("jvmApiCheck")
            dependsOn(jvmApiCheck)
        }
    }

    private fun Project.configureKotlinSourceSets(apiVersion: String, languageVersion: String) {
        configure<KotlinMultiplatformExtension> {
            sourceSets.configureEach {
                languageSettings.apply {
                    this.apiVersion = apiVersion
                    this.languageVersion = languageVersion
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
                            withCompilerArguments {
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
        configure<KotlinMultiplatformExtension> {
            js {
                moduleName = project.name

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

        applyKotlinJsImplicitDependencyWorkaround()

        checkJsTask()
    }

    @OptIn(ExperimentalWasmDsl::class)
    private fun Project.configureWasmJsTarget() {
        configure<KotlinMultiplatformExtension> {
            wasmJs {
                moduleName = project.name

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

        checkWasmJsTask()
    }

    // https://youtrack.jetbrains.com/issue/KT-56025
    private fun Project.applyKotlinJsImplicitDependencyWorkaround() {
        val compileSyncTasks = getCompileSyncTasks()

        tasks.named("jsBrowserProductionWebpack").configure(compileSyncTasks)
        tasks.named("jsBrowserProductionLibraryDistribution").configure(compileSyncTasks)
        tasks.named("jsNodeProductionLibraryDistribution").configure(compileSyncTasks)

        tasks.named("jsNodeTest").configure {
            dependsOn(tasks.getByPath("wasmJsTestTestDevelopmentExecutableCompileSync"))
        }

        tasks.named("jsBrowserTest").configure {
            dependsOn(tasks.getByPath("wasmJsTestTestDevelopmentExecutableCompileSync"))
        }
    }

    // https://youtrack.jetbrains.com/issue/KT-56025
    private fun Project.applyKotlinWasmJsImplicitDependencyWorkaround() {
        val compileSyncTasks = getCompileSyncTasks()

        tasks.named("wasmJsBrowserProductionWebpack").configure(compileSyncTasks)
        tasks.named("wasmJsBrowserProductionLibraryDistribution").configure(compileSyncTasks)
        tasks.named("wasmJsBrowserDistribution").configure(compileSyncTasks)
        tasks.named("wasmJsNodeProductionLibraryDistribution").configure(compileSyncTasks)

        tasks.named("wasmJsBrowserTest").configure {
            dependsOn(tasks.getByPath("jsTestTestDevelopmentExecutableCompileSync"))
        }

        tasks.named("wasmJsNodeTest").configure {
            dependsOn(tasks.getByPath("jsTestTestDevelopmentExecutableCompileSync"))
        }
    }

    private fun Project.getCompileSyncTasks(): Task.() -> Unit = {
        dependsOn(tasks.getByPath("jsProductionLibraryCompileSync"))
        dependsOn(tasks.getByPath("jsProductionExecutableCompileSync"))

        dependsOn(tasks.getByPath("wasmJsProductionLibraryCompileSync"))
        dependsOn(tasks.getByPath("wasmJsProductionExecutableCompileSync"))
    }

    private fun Project.configureJvmTarget(jvmTarget: JvmTarget) {
        configure<KotlinMultiplatformExtension> {
            jvm {
                compilations.configureEach {
                    compileTaskProvider.configure {
                        compilerOptions {
                            withCompilerArguments {
                                requiresJsr305()
                            }
                            this.jvmTarget = jvmTarget
                        }
                    }
                }
            }
        }

        checkJvmTask()
    }
}
