import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libraries.plugins.kotlinx.serialization) apply false
    alias(libraries.plugins.kotlinx.bcv)
    alias(libraries.plugins.detekt)

    id("build-project-default")
    id("build-wrapper-default")
}

run {
    description = "Root Project"
}

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
            "platform",
            "version-catalog",
            "client-sample-cio",
            "client-sample-java",
        )
    )
}

tasks {
    val detektAll by registering(Detekt::class) {
        description = "Run detekt"

        buildUponDefaultConfig = true
        parallel = true
        setSource(projectDir)
        config.setFrom(project.file("./config/detekt/detekt.yml"))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/resources/**", "**/build/**", "**/build.gradle.kts/**", "**/settings.gradle.kts/**")
    }

    // Fix CodeQL workflow execution
    val testClasses by registering
}
