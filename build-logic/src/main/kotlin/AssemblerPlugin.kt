package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.withType

public class AssemblerPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        tasks.withType<Jar>().configureEach {
            manifest.attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version.toString()
            )
        }
    }

    public companion object {
        public const val PLUGIN_ID: String = "build-assembler"
    }
}
