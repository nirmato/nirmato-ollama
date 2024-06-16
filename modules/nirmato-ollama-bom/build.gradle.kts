plugins {
    `java-platform`
    `maven-publish`
}

description = "${rootProject.description} (Bill of Materials)"

val modulesToIncludeInBom = setOf(
    "nirmato-ollama-client",
)

val librariesToIncludeInBom = setOf(
    libraries.kotlin.test,
    libraries.kotlin.test.junit5,
    libraries.kotlinx.coroutines.core,
    libraries.kotlinx.coroutines.test,
    libraries.kotlinx.datetime,
    libraries.kotlinx.io.core,
    libraries.kotlinx.serialization.core,
    libraries.kotlinx.serialization.json,
    libraries.ktor.client,
    libraries.ktor.client.content.negotiation,
    libraries.ktor.client.core,
    libraries.ktor.client.json,
    libraries.ktor.client.logging,
    libraries.ktor.client.mock,
    libraries.ktor.serialization.kotlinx.json,
)

dependencies {
    constraints {
        for (subproject in project.rootProject.subprojects) {
            if (subproject.name in modulesToIncludeInBom) {
                api(subproject)
            }
        }

        val catalog = versionCatalogs.named("libraries")
        for (alias in catalog.libraryAliases.filter { it in librariesToIncludeInBom }) {
            api(alias)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            afterEvaluate { from(components["javaPlatform"]) }
        }
    }
}
