package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ProjectBuildPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = Unit

    public companion object {
        public const val PLUGIN_ID: String = "build-project"
    }
}
