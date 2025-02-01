plugins {
    alias(libraries.plugins.kotlinx.serialization)
    alias(libraries.plugins.kotlinx.kover)

    `maven-publish`

    id("build-kotlin-multiplatform")
    id("build-project-default")
    id("build-maven-publishing-configurer")
}

description = "Ollama Client Coroutines Ktor"

kotlin {
    explicitApi()

    sourceSets {
        val commonMain by getting {
            kotlin {
                srcDirs("src/commonMain/kotlinX")
            }
            dependencies {
                implementation(project(":modules:client-coroutines"))

                implementation(libraries.kotlinx.datetime)
                implementation(libraries.kotlinx.coroutines.core)
                implementation(libraries.kotlinx.serialization.core)
                implementation(libraries.kotlinx.serialization.json)

                implementation(libraries.ktor.client.content.negotiation)
                implementation(libraries.ktor.client.core)
                implementation(libraries.ktor.client.logging)

                implementation("ch.qos.logback:logback-classic:1.5.16")
                implementation(libraries.ktor.serialization.kotlinx.json)
            }
        }
    }
}
