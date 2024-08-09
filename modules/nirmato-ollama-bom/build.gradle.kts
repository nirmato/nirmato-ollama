plugins {
    `java-platform`
    `maven-publish`
}

description = "Bill of Materials"

val modulesToIncludeInBom = setOf(
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
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }
}
