package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin

public class DefaultProjectPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        project.checkKotlinVersion()
    }

    private fun Project.checkKotlinVersion() {
        KOTLIN_PLUGIN_IDS.forEach { pluginId ->
            plugins.withId(pluginId) {
                if (!isAtLeastKotlinVersion(pluginId, 2, 1, 0)) {
                    throw StopExecutionException("You need Kotlin version 2.1.0 or newer")
                }
            }
        }
    }

    private fun Project.isAtLeastKotlinVersion(id: String, major: Int, minor: Int, patch: Int): Boolean {
        val plugin = project.plugins.getPlugin(id) as KotlinBasePlugin
        val (kgpMajor, kgpMinor, kgpPatch) = plugin.pluginVersion
            .substringBefore('-')
            .split(".")
            .map { it.toInt() }

        return kgpMajor > major || (kgpMajor == major && (kgpMinor > minor || (kgpMinor == minor && kgpPatch >= patch)))
    }

    public companion object {
        public const val PLUGIN_ID: String = "build-project-default"

        internal val KOTLIN_PLUGIN_IDS = listOf(
            "org.jetbrains.kotlin.jvm",
            "org.jetbrains.kotlin.multiplatform",
        )
    }
}
