@file:Suppress("UnstableApiUsage")

package build.gradle.plugins.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.RepositoriesMode
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.toolchains.foojay.FoojayToolchainsPlugin
import org.gradle.util.GradleVersion

@Suppress("UnstableApiUsage")
public class SettingsDefaultPlugin : Plugin<Settings> {
    override fun apply(settings: Settings): Unit = settings.run {
        checkMinimumGradleVersion()
        configurePluginManagement()
        configureDependencyResolutionManagement()
        configureFoojayPlugin()
    }

    private fun Settings.configurePluginManagement() {
        settings.pluginManagement {
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

    private fun Settings.configureFoojayPlugin() {
        settings.apply<FoojayToolchainsPlugin>()
    }

    private fun Settings.checkMinimumGradleVersion() {
        if (GradleVersion.current() < MIN_GRADLE_VERSION) {
            error("You need Gradle version $MIN_GRADLE_VERSION or higher, was ${GradleVersion.current()}")
        }
    }

    private companion object {
        val MIN_GRADLE_VERSION: GradleVersion = GradleVersion.version("8.9")
    }
}
