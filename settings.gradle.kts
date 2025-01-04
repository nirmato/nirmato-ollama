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

include("modules:annotations")
include("modules:api")
include("modules:client")

include("publishing:bom")
include("publishing:version-catalog")

if(providers.gradleBooleanProperty("kotlin.targets.jvm.enabled").get()) {
    include("samples:client-sample-cio")
    include("samples:client-sample-java")
}

if(providers.gradleBooleanProperty("kotlin.targets.js.enabled").get()) {
    include("samples:client-sample-nodejs")
}
