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
        languageVersion = providers.gradleProperty("kotlin.javaToolchain.mainJvmCompiler").map(JavaLanguageVersion::of)
    }

    sourceSets {
        all {
            languageSettings.apply {
                apiVersion =  providers.gradleProperty("kotlin.sourceSets.languageSettings.apiVersion").get()
                languageVersion =  providers.gradleProperty("kotlin.sourceSets.languageSettings.languageVersion").get()
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
