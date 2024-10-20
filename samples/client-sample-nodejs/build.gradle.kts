import org.gradle.api.stringProperty

plugins {
    kotlin("multiplatform")

    id("build-project-default")
}

description = "Ollama Client using Ktor Js engine"

kotlin {
    js {
        nodejs()

        binaries.executable()
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

                implementation(libraries.ktor.client.js)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libraries.kotlinx.coroutines.core)
                implementation(libraries.kotlinx.serialization.json)
                implementation(libraries.ktor.client.content.negotiation)
                implementation(libraries.ktor.client.logging)
                implementation(libraries.ktor.serialization.kotlinx.json)
            }
        }
    }
}
