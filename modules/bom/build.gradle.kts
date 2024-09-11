plugins {
    id("java-platform")
    id("build-publishing")
}

description = "BOM"

val me = project
rootProject.subprojects {
    if (name != me.name) {
        me.evaluationDependsOn(path)
    }
}

dependencies {
    constraints {
        rootProject.subprojects.forEach { subproject ->
            if (subproject.plugins.hasPlugin("maven-publish") && !subproject.name.endsWith("version-catalog") && subproject.name != name) {
                subproject.publishing.publications.withType<MavenPublication>().configureEach {
                    if (!artifactId.endsWith("-metadata") && !artifactId.endsWith("-kotlinMultiplatform")) {
                        api("$groupId:$artifactId:$version")
                    }
                }
            }
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("bom") {
            artifactId = "${rootProject.name}-${project.name}"

            from(components["javaPlatform"])
        }
    }
}
