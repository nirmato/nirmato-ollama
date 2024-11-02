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

internal fun Project.getProperty(key: String, environmentKey: String? = null): String? {
    val projectValue = findProperty(key)?.toString()?.takeIf { value -> value.isNotBlank() }
    val systemValue = System.getProperty(key)?.takeIf { value -> value.isNotBlank() }
    val environmentValue = environmentKey?.let { System.getenv(it) } ?.takeIf { value -> value.isNotBlank() }

    return environmentValue ?: systemValue ?: projectValue
}

public fun Project.gradleStringProperties(prefix: String): Provider<MutableMap<String, String>> = providers.gradlePropertiesPrefixedBy(prefix)

public fun Project.gradleStringProperty(name: String): Provider<String> = providers.gradleProperty(name)

public fun Project.gradleBooleanProperty(name: String): Provider<Boolean> = gradleStringProperty(name).map { it.toBoolean() }.orElse(false)

