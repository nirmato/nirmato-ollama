import build.gradle.api.includeModule

pluginManagement {
    includeBuild("build-settings-logic")
    includeBuild("build-logic")
}

plugins {
    id("build-settings-default")
}

rootProject.name = "nirmato-ollama"

includeModule("nirmato-ollama-api")
includeModule("nirmato-ollama-client")
includeModule("nirmato-ollama-client-ktor")
includeModule("nirmato-ollama-bom")
includeModule("nirmato-ollama-version-catalog")

include("samples:nirmato-ollama-client-sample-cio")
include("samples:nirmato-ollama-client-sample-nodejs")
include("samples:nirmato-ollama-client-sample-java")
