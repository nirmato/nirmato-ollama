import org.gradle.api.stringProperty
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")

    id("build-project-default")
}

description = "Ollama Client using Ktor Java engine"

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set("org.nirmato.ollama.client.samples.ApplicationKt")
        }
    }

    jvmToolchain {
        languageVersion = project.stringProperty("kotlin.javaToolchain.mainJvmCompiler").map(JavaLanguageVersion::of)
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

                implementation(libraries.ktor.client.java)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libraries.kotlinx.coroutines.slf4j)
                implementation(libraries.kotlinx.serialization.json)
                implementation(libraries.ktor.client.content.negotiation)
                implementation(libraries.ktor.client.java)
                implementation(libraries.ktor.client.logging)
                implementation(libraries.ktor.serialization.kotlinx.json)
            }
        }
    }
}
