package build.gradle.plugins.build

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.getProperty
import org.gradle.api.gradleBooleanProperty
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin
import org.gradle.plugins.signing.signatory.Signatory
import org.gradle.plugins.signing.type.AbstractSignatureType
import org.gradle.plugins.signing.type.SignatureType
import org.gradle.plugins.signing.type.pgp.ArmoredSignatureType

public class MavenPublishBuildPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        configurePublishPlugin(project)

        configureKotlinJvmPublishing()
        configureKotlinMultiplatformPublishing(project)
//        configurePublications(project)

        if (project.gradleBooleanProperty("org.gradle.publications.signing.enabled").get()) {
            configureSigning()
        }
    }

    private fun configurePublishPlugin(project: Project): Unit = project.run {
        apply<com.vanniktech.maven.publish.MavenPublishPlugin>()

        configure<MavenPublishBaseExtension> {
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, false)

            coordinates(
                groupId = group.toString(),
                artifactId = project.name,
                version = version.toString()
            )

            val base = "github.com/nirmato/nirmato-ollama"

            pom {
                // using providers because the name and description can be set after application of the plugin
                name.set(project.provider { project.name })
                description.set(project.provider { project.description })
                // to be replaced by lazy property
                version = project.version.toString()
                url.set("https://$base")
                inceptionYear.set("2024")

                organization {
                    name = "nirmato"
                    url = "https://github.com/nirmato"
                }

                developers {
                    developer {
                        name = "The Nirmato Team"
                    }
                }

                issueManagement {
                    system = "GitHub"
                    url = "https://$base/issues"
                }

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    url.set("https://$base")
                    connection.set("scm:git:git://$base.git")
                    developerConnection.set("scm:git:ssh://git@$base.git")
                }
            }
        }
    }

    private fun configurePublications(project: Project) {
        val localMavenDirectory = project.rootProject.layout.buildDirectory.dir("local-m2")
        project.configure<PublishingExtension> {
            // configureEach reacts on new publications being registered and configures them too
            publications.withType<MavenPublication>().configureEach {
                val base = "github.com/nirmato/nirmato-ollama"

                pom {
                    // using providers because the name and description can be set after application of the plugin
                    name.set(project.provider { project.name })
                    description.set(project.provider { project.description })
                    // to be replaced by lazy property
                    version = project.version.toString()
                    url.set("https://$base")
                    inceptionYear.set("2024")

                    organization {
                        name = "nirmato"
                        url = "https://github.com/nirmato"
                    }

                    developers {
                        developer {
                            name = "The Nirmato Team"
                        }
                    }

                    issueManagement {
                        system = "GitHub"
                        url = "https://$base/issues"
                    }

                    licenses {
                        license {
                            name.set("Apache License 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    scm {
                        url.set("https://$base")
                        connection.set("scm:git:git://$base.git")
                        developerConnection.set("scm:git:ssh://git@$base.git")
                    }
                }
            }

            repositories {
                maven {
                    name = "local"
                    setUrl(localMavenDirectory)
                }
            }
        }
    }

    private fun Project.configureKotlinMultiplatformPublishing(project: Project) {
        configure<PublishingExtension> {
            publications.withType<MavenPublication>().configureEach {
                if (project.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
                    val name = this.name

                    project.afterEvaluate {
                        artifactId = if (name == "kotlinMultiplatform") {
                            "${rootProject.name}-${project.name}"
                        } else {
                            "${rootProject.name}-${project.name}-$name"
                        }
                    }
                }

                project.afterEvaluate {
                    if (artifactId == "version-catalog" || artifactId == "bom") {
                        artifactId = "${rootProject.name}-${project.name}"
                    }
                }
            }
        }
    }

    private fun Project.configureKotlinJvmPublishing() {
        if (project.plugins.hasPlugin("org.jetbrains.kotlin.jvm")) {
            // sourcesJar is not added by default by the Kotlin JVM plugin
            configure<JavaPluginExtension> {
                withSourcesJar()
            }

            configure<PublishingExtension> {
                publications {
                    register<MavenPublication>("maven") {
                        // java components have the sourcesJar and the Kotlin artifacts
                        from(components["java"])
                    }
                }
            }
        }
    }

    private fun Project.configureSigning() {
        apply<SigningPlugin>()

        configure<SigningExtension> {
            val signingKeyId = project.getProperty(key = "gpg.signing.key.id", environmentKey = "GPG_SIGNING_KEY_ID")

            logger.info("Signing key id: $signingKeyId")
            if (signingKeyId != null) {
                val signingKey = project.getProperty(key = "gpg.signing.key", environmentKey = "GPG_SIGNING_KEY")?.let { String(Base64.getDecoder().decode(it)) }
                val signingPassword = project.getProperty(key = "gpg.signing.passphrase", environmentKey = "GPG_SIGNING_PASSPHRASE").orEmpty()

                useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)

                val publishing: PublishingExtension by project
                sign(publishing.publications)
            }
        }

        // see https://youtrack.jetbrains.com/issue/KT-61313/
        // see https://github.com/gradle/gradle/issues/26132
        project.plugins.withId("org.jetbrains.kotlin.multiplatform") {
            project.tasks.withType<Sign>().configureEach {
                signatureType = WorkaroundSignatureType(signatureType ?: ArmoredSignatureType(), project.layout.buildDirectory.dir("signatures/${name}"))
            }
        }

        fixOverlappingOutputsForSigningTask()
    }

    private class WorkaroundSignatureType(@Nested val actual: SignatureType, @Internal val directory: Provider<Directory>) : AbstractSignatureType() {
        override fun fileFor(toSign: File): File {
            val original = super.fileFor(toSign)
            return directory.get().file(original.name).asFile
        }

        override fun sign(signatory: Signatory?, toSign: File?): File {
            // needs to call super and not actual because this is what will call fileFor
            return super.sign(signatory, toSign)
        }

        override fun getExtension(): String {
            return actual.extension
        }

        override fun sign(signatory: Signatory?, toSign: InputStream?, destination: OutputStream?) {
            actual.sign(signatory, toSign, destination)
        }
    }

    public companion object {
        public const val PLUGIN_ID: String = "build-maven-publish"
        public const val MAVEN_PUBLISH_PLUGIN_ID: String = "maven-publish"
    }
}

// Resolves issues with .asc task output of the sign task of native targets.
// See: https://github.com/gradle/gradle/issues/26132
// And: https://youtrack.jetbrains.com/issue/KT-46466
private fun Project.fixOverlappingOutputsForSigningTask() {
    tasks.withType<Sign>().configureEach {
        val publicationName = name.removePrefix("sign").removeSuffix("Publication")

        // These tasks only exist for native targets, hence findByName() to avoid trying to find them for other targets

        // Task ':linkDebugTest<platform>' uses this output of task ':sign<platform>Publication' without declaring an explicit or implicit dependency
        tasks.findByName("linkDebugTest$publicationName")?.let {
            mustRunAfter(it)
        }
        // Task ':compileTestKotlin<platform>' uses this output of task ':sign<platform>Publication' without declaring an explicit or implicit dependency
        tasks.findByName("compileTestKotlin$publicationName")?.let {
            mustRunAfter(it)
        }
    }
}

