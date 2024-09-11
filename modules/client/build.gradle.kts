import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    alias(libraries.plugins.kotlinx.serialization)
    alias(libraries.plugins.dokka.gradle.plugin)
    alias(libraries.plugins.kotlinx.kover)

    id("build-multiplatform")
    id("build-project-default")
    id("build-publishing")
}

description = "Ollama Client"

kotlin {
    explicitApi()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.ExperimentalStdlibApi")
                optIn("kotlin.RequiresOptIn")
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("kotlin.time.ExperimentalTime")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }

        matching { it.name.endsWith("Test") }.configureEach {
            languageSettings.apply {
                optIn("kotlinx.coroutines.FlowPreview")
            }
        }

        val commonMain by getting {
            kotlin {
                srcDirs("src/commonMain/kotlinX")
            }
            dependencies {
                api(project(":api"))
                api(libraries.kotlinx.datetime)

                implementation(libraries.kotlinx.coroutines.core)
                implementation(libraries.kotlinx.serialization.core)
                implementation(libraries.kotlinx.serialization.json)
                implementation(libraries.ktor.client.content.negotiation)
                implementation(libraries.ktor.client.core)
                implementation(libraries.ktor.client.logging)
                implementation(libraries.ktor.serialization.kotlinx.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libraries.kotlin.test)
                implementation(libraries.ktor.client.mock)
                implementation(libraries.kotlinx.coroutines.test)
            }
        }

        val jvmTest by getting
    }
}

tasks {
    withType<DokkaTaskPartial>().configureEach {
        dokkaSourceSets.configureEach {
            documentedVisibilities.set(Visibility.values().toSet())
        }
        failOnWarning.set(true)
        offlineMode.set(true)
    }
}

publishing {
    publications.configureEach {
        with(this as MavenPublication) {
            artifactId = if(name == "kotlinMultiplatform") {
                "${rootProject.name}-${project.name}"
            } else {
                "${rootProject.name}-${project.name}-$name"
            }
        }
    }
}
