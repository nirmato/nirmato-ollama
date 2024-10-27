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
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }

    sourceSets {
        configureEach {
            languageSettings.apply {
                this.apiVersion = providers.gradleProperty("kotlin.sourceSets.languageSettings.apiVersion").get()
                this.languageVersion = providers.gradleProperty("kotlin.sourceSets.languageSettings.languageVersion").get()
                this.progressiveMode = true
            }
        }
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
