@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("multiplatform")

    id("build-project-default")
}

description = "Ollama Client using Ktor Java engine"

kotlin {
    compilerOptions {
        apiVersion = providers.gradleProperty("kotlin.compilerOptions.apiVersion").map(KotlinVersion::fromVersion)
        languageVersion = providers.gradleProperty("kotlin.compilerOptions.languageVersion").map(KotlinVersion::fromVersion)
        progressiveMode = false

        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }

    jvm {
        mainRun {
            mainClass.set("org.nirmato.ollama.client.samples.ApplicationKt")
        }
    }

    jvmToolchain {
        languageVersion = providers.gradleProperty("kotlin.javaToolchain.mainJvmCompiler").map(JavaLanguageVersion::of)
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:client"))

                implementation(libraries.ktor.client.java)
                implementation(libraries.kotlinx.datetime)
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
