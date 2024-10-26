import org.gradle.api.initialization.includeModule
import org.gradle.api.initialization.includeSample

pluginManagement {
    includeBuild("build-settings-logic")
    includeBuild("build-logic")
}

plugins {
    id("build-settings-default")
}

run {
    rootProject.name = "nirmato-ollama"
}

includeModule("api")
includeModule("client")
includeModule("client-ktor")
includeModule("platform")
includeModule("version-catalog")

includeSample("client-sample-cio")
includeSample("client-sample-nodejs")
includeSample("client-sample-java")
