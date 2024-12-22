plugins {
    `maven-publish`

    id("version-catalog")
    id("build-maven-publishing-configurer")
}

description = "Version Catalog"

catalog {
    versionCatalog {
        version("kotlin", libraries.versions.kotlin.get())

        rootProject.subprojects.forEach { subproject ->
            if (subproject.plugins.hasPlugin("maven-publish") && subproject.name != name) {
                subproject.publishing.publications.withType<MavenPublication>().configureEach {
                    if (!artifactId.endsWith("-metadata") && !artifactId.endsWith("-kotlinMultiplatform")) {
                        library(artifactId, "$groupId:$artifactId:$version")
                    }
                }
            }
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("versionCatalog") {
            artifactId = "${rootProject.name}-${project.name}"

            from(components["versionCatalog"])
        }
    }
}
