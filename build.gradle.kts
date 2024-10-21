import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

plugins {
    alias(libraries.plugins.kotlinx.serialization) apply false
    alias(libraries.plugins.kotlinx.bcv)
    alias(libraries.plugins.detekt)

    id("build-project-default")
    id("build-wrapper-default")
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
            "client-sample-java",
        )
    )
}

plugins.withType<YarnPlugin> {
    yarn.apply {
        download = false
        ignoreScripts = false
        lockFileDirectory = rootDir.resolve("gradle/js")
        reportNewYarnLock = true
        yarnLockAutoReplace = true
        yarnLockMismatchReport = YarnLockMismatchReport.FAIL

        resolution("braces", "3.0.3")
        resolution("follow-redirects", "1.15.6")
        resolution("body-parser", "1.20.3")
    }
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
