plugins {
    alias(libraries.plugins.kotlinx.serialization)
    alias(libraries.plugins.kotlinx.kover)

    `maven-publish`

    id("build-kotlin-multiplatform")
    id("build-project-default")
    id("build-maven-publishing-configurer")
}

description = "Ollama Client"

kotlin {
    explicitApi()

    compilerOptions {
        optIn.addAll(
            listOf(
                "kotlin.ExperimentalStdlibApi",
                "kotlin.RequiresOptIn",
                "kotlin.contracts.ExperimentalContracts",
                "kotlin.time.ExperimentalTime",
                "kotlinx.coroutines.ExperimentalCoroutinesApi",
                "kotlinx.serialization.ExperimentalSerializationApi",
            )
        )
    }

    sourceSets {
        matching { it.name.endsWith("Test") }.configureEach {
            compilerOptions {
                optIn.add("kotlinx.coroutines.FlowPreview")
            }
        }

        val commonMain by getting {
            kotlin {
                srcDirs("src/commonMain/kotlinX")
            }
            dependencies {
                api(project(":modules:api"))

                implementation(libraries.kotlinx.datetime)
                implementation(libraries.kotlinx.coroutines.core)
                implementation(libraries.kotlinx.serialization.core)
                implementation(libraries.kotlinx.serialization.json)
                implementation(libraries.ktor.client.content.negotiation)
                implementation(libraries.ktor.client.core)
                implementation(libraries.ktor.client.logging)
                implementation("ch.qos.logback:logback-classic:1.5.15")
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
