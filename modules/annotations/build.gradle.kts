plugins {
    alias(libraries.plugins.kotlinx.serialization)
    alias(libraries.plugins.kotlinx.kover)

    id("build-kotlin-multiplatform")
    id("build-project-default")
    id("build-maven-publishing-configurer")
}

description = "Nirmato Annotations"

kotlin {
    explicitApi()

    sourceSets {
        val commonMain by getting {
            kotlin {
                srcDirs("src/commonMain/kotlinX")
            }
        }
    }
}
