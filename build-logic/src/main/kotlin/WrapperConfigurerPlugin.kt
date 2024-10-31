package build.gradle.plugins.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType
import org.gradle.kotlin.dsl.named

public class WrapperConfigurerPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        if (project != rootProject) {
            throw StopExecutionException("Gradle wrapper plugin should only be applied to the root project.")
        }

        tasks.named<Wrapper>("wrapper") {
            gradleVersion = providers.gradleProperty("org.gradle.version").get()
            distributionType = DistributionType.ALL

            doLast {
                logger.info("Using Gradle wrapper $gradleVersion")
            }
        }
    }
}
