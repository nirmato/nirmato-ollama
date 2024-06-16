import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

configurations.all {
    resolutionStrategy {
        failOnNonReproducibleResolution()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${getKotlinPluginVersion()}")
}

kotlin {
    explicitApi()

    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }

    sourceSets {
        main {
            kotlin {
                srcDirs("src/main/kotlinX")
            }
        }
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
