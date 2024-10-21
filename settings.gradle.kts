import org.gradle.api.initialization.includeModule

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
includeModule("bom")
includeModule("version-catalog")

include("samples:client-sample-cio")
include("samples:client-sample-nodejs")
include("samples:client-sample-java")
