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
    api(libraries.detekt.gradle.plugin)
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
        register("MavenPublishingPlugin") {
            id = "build-maven-publishing-configurer"
            implementationClass = "build.gradle.plugins.build.MavenPublishConfigurerPlugin"
        }
        register("KotlinMultiplatformBuildPlugin") {
            id = "build-kotlin-multiplatform"
            implementationClass = "build.gradle.plugins.build.KotlinMultiplatformBuildPlugin"
        }
        register("AssemblerPlugin") {
            id = "build-assembler"
            implementationClass = "build.gradle.plugins.build.AssemblerPlugin"
        }
        register("WrapperConfigurerPlugin") {
            id = "build-wrapper-configurer"
            implementationClass = "build.gradle.plugins.build.WrapperConfigurerPlugin"
        }
        register("DetektConfigurerPlugin") {
            id = "build-detekt-configurer"
            implementationClass = "build.gradle.plugins.build.DetektConfigurerPlugin"
        }
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}
