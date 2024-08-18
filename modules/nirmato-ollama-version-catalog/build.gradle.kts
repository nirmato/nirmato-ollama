plugins {
    `version-catalog`
    `maven-publish`
}

description = "Gradle Version Catalog"

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

catalog {
    versionCatalog {
        version("kotlin", libraries.versions.kotlin.asProvider().get())

        val catalog = versionCatalogs.named("libraries")
        for (alias in catalog.libraryAliases.filter { it in librariesToIncludeInBom }) {
            library(alias, alias.toString())
        }

        val modules = mutableListOf<String>()
        for (subproject in project.rootProject.subprojects) {
            if (subproject.name in modulesToIncludeInBom) {
                library(subproject.name, "${subproject.group}:${subproject.name}:${subproject.version}")
                modules.add(subproject.name)
            }
        }

        bundle("nirmato-ollama-all", modules)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["versionCatalog"])

            pom {
                name.set(artifactId)
                description.set(project.description)
                url.set("https://github.com/nirmato/nirmato-ollama")
                inceptionYear.set("2024")

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    val base = "github.com/nirmato/nirmato-ollama"

                    url.set("https://$base")
                    connection.set("scm:git:git://$base.git")
                    developerConnection.set("scm:git:ssh://git@$base.git")
                }
            }
        }
    }
}
