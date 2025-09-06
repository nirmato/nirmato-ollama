import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

configurations.configureEach {
    resolutionStrategy {
        failOnNonReproducibleResolution()
    }
}

dependencies {
    implementation(libraries.kotlin.gradle.plugin)
    implementation(libraries.detekt.gradle.plugin)
    implementation(libraries.gradle.maven.publish.plugin)
}

sourceSets {
    main {
        kotlin {
            srcDirs("src/main/kotlinX")
        }
    }
}

kotlin {
    explicitApi()

    compilerOptions {
        apiVersion = providers.gradleProperty("kotlin.compilerOptions.apiVersion").map(KotlinVersion::fromVersion)
        languageVersion = providers.gradleProperty("kotlin.compilerOptions.languageVersion").map(KotlinVersion::fromVersion)
        progressiveMode = true

        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }

    jvmToolchain {
        languageVersion = providers.gradleProperty("kotlin.javaToolchain.mainJvmCompiler").map(JavaLanguageVersion::of)
    }
}

gradlePlugin {
    plugins {
        register("ProjectBuildPlugin") {
            id = "build-project"
            implementationClass = "build.gradle.plugins.build.ProjectBuildPlugin"
        }
        register("MavenPublishBuildPlugin") {
            id = "build-maven-publish"
            implementationClass = "build.gradle.plugins.build.MavenPublishBuildPlugin"
        }
        register("KotlinMultiplatformBuildPlugin") {
            id = "build-kotlin-multiplatform"
            implementationClass = "build.gradle.plugins.build.KotlinMultiplatformBuildPlugin"
        }
        register("AssemblerPlugin") {
            id = "build-assembler"
            implementationClass = "build.gradle.plugins.build.AssemblerPlugin"
        }
        register("WrapperBuildPlugin") {
            id = "build-wrapper"
            implementationClass = "build.gradle.plugins.build.WrapperBuildPlugin"
        }
        register("DetektBuildPlugin") {
            id = "build-detekt"
            implementationClass = "build.gradle.plugins.build.DetektBuildPlugin"
        }
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}
