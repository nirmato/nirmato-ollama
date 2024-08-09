import org.jetbrains.kotlin.config.ApiVersion
import org.jetbrains.kotlin.config.LanguageVersion

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

    sourceSets {
        all {
            languageSettings.apply {
                apiVersion = ApiVersion.KOTLIN_1_7.toString()
                languageVersion = LanguageVersion.KOTLIN_2_0.toString()
                progressiveMode = true
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(project(":nirmato-ollama-client"))

                implementation(libraries.ktor.client.js)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libraries.kotlinx.coroutines.core.js)
                implementation(libraries.kotlinx.serialization.json.js)
                implementation(libraries.ktor.client.content.negotiation.js)
                implementation(libraries.ktor.client.js.js)
                implementation(libraries.ktor.client.logging.js)
                implementation(libraries.ktor.serialization.kotlinx.json.js)
            }
        }
    }
}
