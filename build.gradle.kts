plugins {
    alias(libraries.plugins.kotlinx.serialization) apply false
    alias(libraries.plugins.kotlinx.bcv)
    alias(libraries.plugins.detekt)

    id("build-project-default")
    id("build-wrapper-configurer")
    id("build-detekt-configurer")
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
            "client-sample-cio",
        )
    )

    nonPublicMarkers.add("org.nirmato.ollama.annotations.InternalApi")
}

tasks {
    // Fix CodeQL workflow execution
    val testClasses by registering
}
