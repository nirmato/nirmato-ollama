package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.isRootProject
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType
import org.gradle.kotlin.dsl.named

public class DefaultWrapperPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        if (!project.isRootProject) {
            project.logger.warn("Gradle wrapper plugin should only be applied to the root project.")
        }

        project.tasks.named<Wrapper>("wrapper") {
            gradleVersion = project.providers.gradleProperty("org.gradle.version").get()
            distributionType = DistributionType.ALL

            doLast {
                println("Gradle wrapper version: $gradleVersion")
            }
        }

    }
}
