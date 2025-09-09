@file:Suppress("UnstableApiUsage")

package build.gradle.plugins.settings

import org.gradle.api.GradleException
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.RepositoriesMode
import org.gradle.kotlin.dsl.assign
import org.gradle.util.GradleVersion

public class ProjectSettingsPlugin : Plugin<Settings> {
    override fun apply(settings: Settings): Unit = settings.run {
        requireMinimumGradleVersion()
        requireJavaRuntimeVersion()

        configurePluginManagement()
        configureDependencyResolutionManagement()
    }

    private fun Settings.configurePluginManagement() {
        pluginManagement {
            repositories {
                gradlePluginPortal {
                    content {
                        includeGroupAndSubgroups("com.gradle")
                        includeGroupAndSubgroups("org.gradle")
                    }
                }
                mavenCentral()
            }
        }
    }

    private fun Settings.configureDependencyResolutionManagement() {
        dependencyResolutionManagement {
            repositoriesMode = RepositoriesMode.PREFER_PROJECT

            repositories {
                gradlePluginPortal {
                    content {
                        includeGroupAndSubgroups("com.gradle")
                        includeGroupAndSubgroups("org.gradle")
                    }
                }
                mavenCentral()
            }

            versionCatalogs {
                create("libraries") {
                    from(layout.rootDirectory.files("gradle/catalogs/libraries.versions.toml"))
                }
            }
        }
    }

    private fun requireMinimumGradleVersion() {
        if (GradleVersion.current() < MINIMUM_GRADLE_VERSION) {
            throw GradleException("You need Gradle version ${MINIMUM_GRADLE_VERSION.baseVersion} or higher, but was ${GradleVersion.current().baseVersion}")
        }
    }

    private fun requireJavaRuntimeVersion() {
        if (JavaVersion.current() < MINIMUM_JAVA_VERSION) {
            throw GradleException("You need Java version $MINIMUM_JAVA_VERSION or higher, but was ${JavaVersion.current()}")
        }
    }

    private companion object {
        const val PLUGIN_ID: String = "build-settings"
        val MINIMUM_GRADLE_VERSION: GradleVersion = GradleVersion.version("9.0.0")
        val MINIMUM_JAVA_VERSION: JavaVersion = JavaVersion.toVersion("17")
    }
}
