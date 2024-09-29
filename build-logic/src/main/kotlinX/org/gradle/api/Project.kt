package org.gradle.api

import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

internal val Project.librariesCatalog
    get() = project.extensions.getByType<VersionCatalogsExtension>().named("libraries")

internal fun Project.getDependency(name: String): Provider<MinimalExternalModuleDependency> {
    return librariesCatalog.findLibrary(name).orElseThrow { NoSuchElementException("Could not find library $name") }
}

internal fun Project.getDependencyOrNull(name: String): Provider<MinimalExternalModuleDependency>? {
    return librariesCatalog.findLibrary(name).orElseGet { null }
}

internal fun Project.getVersion(name: String): String {
    return getVersionOrNull(name) ?: throw NoSuchElementException("Could not find version $name")
}

internal fun Project.getVersionOrNull(name: String): String? {
    return librariesCatalog.findVersion(name).orElseGet { null }?.requiredVersion
}

internal fun Project.getBundleOrNull(name: String): Provider<ExternalModuleDependencyBundle>? {
    return librariesCatalog.findBundle(name).orElseGet { null }
}

internal val isRunningOnCI: Boolean
    get() = System.getenv("CI") != null

internal val Project.isRootProject: Boolean
    get() = this == rootProject

internal fun Project.getProperty(projectKey: String, environmentKey: String): String? {
    return if (project.hasProperty(projectKey)) {
        project.property(projectKey) as? String?
    } else {
        System.getenv(environmentKey)
    }
}

internal fun Project.stringProperties(prefix: String): Provider<MutableMap<String, String>> {
    return providers.gradlePropertiesPrefixedBy(prefix)
}

internal fun Project.stringProperty(name: String): Provider<String> = providers.gradleProperty(name)

internal fun Project.booleanProperty(name: String, defaultValue: Boolean): Provider<Boolean> {
    return stringProperty(name).map { it.toBoolean() }.orElse(defaultValue)
}
