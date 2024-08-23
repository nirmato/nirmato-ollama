plugins {
    id("java-platform")
    id("maven-publish")
}

description = "Bill of Materials"

val modulesToIncludeInBom = setOf(
    "nirmato-ollama-api",
    "nirmato-ollama-client",
)

dependencies {
    constraints {
        for (subproject in project.rootProject.subprojects) {
            if (subproject.name in modulesToIncludeInBom) {
                api(subproject)
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("bom") {
            groupId = group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["javaPlatform"])

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
