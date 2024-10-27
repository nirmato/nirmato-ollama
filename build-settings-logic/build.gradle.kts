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
    api(libraries.foojay.resolver)
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
        register("SettingsDefaultPlugin") {
            id = "build-settings-default"
            implementationClass = "build.gradle.plugins.settings.SettingsDefaultPlugin"
        }
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}
