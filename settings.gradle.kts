import org.gradle.api.provider.gradleBooleanProperty

pluginManagement {
    includeBuild("build-settings-logic")
    includeBuild("build-logic")
}

plugins {
    id("build-settings-default")
    id("build-foojay")
}

rootProject.name = "nirmato-ollama"

include("modules:client")
include("modules:client-ktor")

include("publishing:bom")
include("publishing:version-catalog")

include("reporting:kover")

if(providers.gradleBooleanProperty("kotlin.targets.jvm.enabled").get()) {
    include("samples:client-sample-cio")
}

if(providers.gradleBooleanProperty("kotlin.targets.js.enabled").get()) {
    include("samples:client-sample-nodejs")
}
