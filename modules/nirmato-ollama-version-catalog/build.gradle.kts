plugins {
    id("version-catalog")

    id("build-publishing")
}

description = "Gradle Version Catalog"

catalog {
    versionCatalog {
        version("kotlin", libraries.versions.kotlin.asProvider().get())

        rootProject.subprojects.forEach { subproject ->
            if (subproject.plugins.hasPlugin("maven-publish") && !subproject.name.endsWith("version-catalog")) {
                subproject.publishing.publications.withType<MavenPublication>().configureEach {
                    library(artifactId, "$groupId:$artifactId:$version")
                }
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("version-catalog") {
            from(components["versionCatalog"])
        }
    }
}
