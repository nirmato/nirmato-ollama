import build.gradle.api.includeModule

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

include("samples:nirmato-ollama-client-sample-cio")
include("samples:nirmato-ollama-client-sample-nodejs")
include("samples:nirmato-ollama-client-sample-java")
