import org.gradle.api.provider.gradleBooleanProperty

plugins {
    alias(libraries.plugins.kotlinx.serialization) apply false
    alias(libraries.plugins.kotlinx.bcv)
    alias(libraries.plugins.detekt)

    id("build-project")
    id("build-wrapper")
    id("build-detekt")
}

description = "Root Project"

allprojects {
    group = "org.nirmato.ollama"

    configurations.configureEach {
        resolutionStrategy {
            failOnNonReproducibleResolution()
        }
    }
}

apiValidation {
    ignoredPackages.add("org.nirmato.ollama.internal")

    ignoredProjects.addAll(
        listOf(
            "bom",
            "version-catalog",
        )
    )

    if (providers.gradleBooleanProperty("kotlin.targets.jvm.enabled").get()) {
        ignoredProjects.add("client-sample-cio")
    }

    nonPublicMarkers.add("org.nirmato.ollama.annotations.InternalApi")
}

tasks {
    // Fix CodeQL workflow execution
    val testClasses by registering
}
