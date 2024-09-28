package org.gradle.api

import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.versionCatalog
    get() = project.extensions.getByType<VersionCatalogsExtension>().named("libraries")

public val isRunningOnCI: Boolean
    get() = System.getenv("CI") != null

public val Project.isRootProject: Boolean
    get() = this == rootProject

public fun Project.getProperty(projectKey: String, environmentKey: String): String? {
    return if (project.hasProperty(projectKey)) {
        project.property(projectKey) as? String?
    } else {
        System.getenv(environmentKey)
    }
}
