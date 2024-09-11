package build.gradle.plugins.build

import build.gradle.dsl.withCompilerArguments
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.config.ApiVersion
import org.jetbrains.kotlin.config.LanguageVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

public class MultiplatformPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.apply<KotlinMultiplatformPluginWrapper>()

        project.configure<KotlinMultiplatformExtension> {
            configureAllTargets()
            configureJvmTarget()
            configureJsTarget()
            configureKotlinSourceSets()
        }

        project.configureJsPlatform()
        project.configureJvmPlatform()
    }

    private fun Project.configureJsPlatform() {
        applyKotlinJsImplicitDependencyWorkaround()

        checkJsTask()
    }

    private fun Project.checkJsTask() {
        val jsTest = tasks.named("jsTest")

        tasks.register("checkJs") {
            group = "verification"
            description = "Runs all checks for the Kotlin/JS platform."
            dependsOn(jsTest)
        }
    }

    private fun Project.configureJvmPlatform() {
        checkJvmTask()
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

    private fun KotlinMultiplatformExtension.configureKotlinSourceSets() {
        sourceSets.configureEach {
            languageSettings.apply {
                apiVersion = ApiVersion.KOTLIN_1_7.toString()
                languageVersion = LanguageVersion.KOTLIN_2_0.toString()
                progressiveMode = true
            }
        }
    }

    private fun KotlinMultiplatformExtension.configureAllTargets() {
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

    private fun KotlinMultiplatformExtension.configureJsTarget() {
        js {
            this.moduleName = this.project.name

            this.browser()
            this.nodejs {
                this.testTask {
                    this.useMocha {
                        this.timeout = "60s"
                    }
                }
            }

            this.binaries.executable()
//            this.binaries.library()
        }
    }

    // https://youtrack.jetbrains.com/issue/KT-56025
    private fun Project.applyKotlinJsImplicitDependencyWorkaround() {
        val configureJs: Task.() -> Unit = {
//            dependsOn(tasks.getByPath(":${project.name}:jsDevelopmentLibraryCompileSync"))
            dependsOn(tasks.getByPath(":${project.name}:jsDevelopmentExecutableCompileSync"))
//            dependsOn(tasks.getByPath(":${project.name}:jsProductionLibraryCompileSync"))
            dependsOn(tasks.getByPath(":${project.name}:jsProductionExecutableCompileSync"))
            dependsOn(tasks.getByPath(":${project.name}:jsTestTestDevelopmentExecutableCompileSync"))
        }
        tasks.named("jsBrowserProductionWebpack").configure(configureJs)
//        tasks.named("jsBrowserProductionLibraryPrepare").configure(configureJs)
//        tasks.named("jsNodeProductionLibraryPrepare").configure(configureJs)
    }

    private fun KotlinMultiplatformExtension.configureJvmTarget() {
        jvm {
            this.compilations.configureEach {
                this.compileTaskProvider.configure {
                    this.compilerOptions {
                        this.withCompilerArguments {
                            this.requiresJsr305()
                        }
                        this.jvmTarget.set(JvmTarget.JVM_17)
                    }
                }
            }
        }
    }
}
