package org.gradle.api

import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

internal val Project.librariesCatalog
    get() = project.extensions.getByType<VersionCatalogsExtension>().named("libraries")

internal fun Project.getCatalogDependency(name: String): Provider<MinimalExternalModuleDependency> {
    return librariesCatalog.findLibrary(name).orElseThrow { NoSuchElementException("Could not find library $name") }
}

internal fun Project.getCatalogDependencyOrNull(name: String): Provider<MinimalExternalModuleDependency>? {
    return librariesCatalog.findLibrary(name).orElseGet { null }
}

internal fun Project.getCatalogVersion(name: String): String {
    return getCatalogVersionOrNull(name) ?: throw NoSuchElementException("Could not find version $name")
}

internal fun Project.getCatalogVersionOrNull(name: String): String? {
    return librariesCatalog.findVersion(name).orElseGet { null }?.requiredVersion
}

internal fun Project.getCatalogBundleOrNull(name: String): Provider<ExternalModuleDependencyBundle>? {
    return librariesCatalog.findBundle(name).orElseGet { null }
}

internal val isRunningOnCI: Boolean
    get() = System.getenv("CI") != null

public val Project.isRootProject: Boolean
    get() = this == rootProject

internal fun Project.getProperty(projectKey: String, environmentKey: String): String? {
    return if (project.hasProperty(projectKey)) {
        project.property(projectKey) as? String?
    } else {
        System.getenv(environmentKey)
    }
}

public fun Project.stringProperties(prefix: String): Provider<MutableMap<String, String>> {
    return providers.gradlePropertiesPrefixedBy(prefix)
}

public fun Project.stringProperty(name: String): Provider<String> = providers.gradleProperty(name)

public fun Project.booleanProperty(name: String, defaultValue: Boolean): Provider<Boolean> {
    return stringProperty(name).map { it.toBoolean() }.orElse(defaultValue)
}
