package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property

public class DefaultProjectPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // empty
    }
}

public val isRunningOnCI: Boolean
    get() = System.getenv("CI") != null

public infix fun <T> Property<T>.by(value: T) {
    set(value)
}

public fun Project.getProperty(projectKey: String, environmentKey: String): String? {
    return if (project.hasProperty(projectKey)) {
        project.property(projectKey) as? String?
    } else {
        System.getenv(environmentKey)
    }
}
