import org.gradle.api.initialization.includeModule
import org.gradle.api.initialization.includeSample
import org.gradle.api.provider.gradleBooleanProperty

pluginManagement {
    includeBuild("build-settings-logic")
    includeBuild("build-logic")
}

plugins {
    id("build-settings-default")
}

rootProject.name = "nirmato-ollama"

includeModule("api")
includeModule("client")
includeModule("client-ktor")
includeModule("platform")
includeModule("version-catalog")

if(providers.gradleBooleanProperty("kotlin.targets.jvm.enabled").get()) {
    includeSample("client-sample-cio")
    includeSample("client-sample-java")
}

if(providers.gradleBooleanProperty("kotlin.targets.js.enabled").get()) {
    includeSample("client-sample-nodejs")
}
