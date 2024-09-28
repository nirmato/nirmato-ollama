import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")

    id("build-project-default")
}

description = "Ollama Client using Ktor CIO engine"

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set("org.nirmato.ollama.client.samples.ApplicationKt")
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                apiVersion = "1.7"
                languageVersion = "2.0"
                progressiveMode = true
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(project(":client-ktor"))

                implementation(libraries.ktor.client.cio)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libraries.kotlinx.coroutines.slf4j)
                implementation(libraries.kotlinx.serialization.json.jvm)
                implementation(libraries.ktor.client.cio.jvm)
                implementation(libraries.ktor.client.content.negotiation.jvm)
                implementation(libraries.ktor.client.logging.jvm)
                implementation(libraries.ktor.serialization.kotlinx.json.jvm)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(libraries.kotlin.test.junit5)
                implementation(libraries.kotlinx.coroutines.test.jvm)
            }

            tasks {
                named<Test>("jvmTest") {
                    useJUnitPlatform()
                }
            }
        }
    }
}
