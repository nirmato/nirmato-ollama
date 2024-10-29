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
    api(libraries.kotlin.gradle.plugin)
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
        register("DefaultProjectPlugin") {
            id = "build-project-default"
            implementationClass = "build.gradle.plugins.build.DefaultProjectPlugin"
        }
        register("PublishingPlugin") {
            id = "build-publishing"
            implementationClass = "build.gradle.plugins.build.PublishingPlugin"
        }
        register("MultiplatformPlugin") {
            id = "build-multiplatform"
            implementationClass = "build.gradle.plugins.build.MultiplatformPlugin"
        }
        register("AssemblerPlugin") {
            id = "build-assembler"
            implementationClass = "build.gradle.plugins.build.AssemblerPlugin"
        }
        register("DefaultWrapperPlugin") {
            id = "build-wrapper-default"
            implementationClass = "build.gradle.plugins.build.DefaultWrapperPlugin"
        }
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}
