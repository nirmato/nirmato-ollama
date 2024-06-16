package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ProjectDefaultPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
       // empty
    }
}
