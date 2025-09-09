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
    implementation(libraries.foojay.resolver)
}

kotlin {
    explicitApi()

    compilerOptions {
        apiVersion = providers.gradleProperty("kotlin.compilerOptions.apiVersion").map(KotlinVersion::fromVersion)
        languageVersion = providers.gradleProperty("kotlin.compilerOptions.languageVersion").map(KotlinVersion::fromVersion)
        progressiveMode = true

        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }

    coreLibrariesVersion = providers.gradleProperty("kotlin.compilerOptions.apiVersion").map(KotlinVersion::fromVersion).get().toString()

    jvmToolchain {
        languageVersion = providers.gradleProperty("kotlin.javaToolchain.mainJvmCompiler").map(JavaLanguageVersion::of)
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
        register("ProjectSettingsPlugin") {
            id = "build-settings"
            implementationClass = "build.gradle.plugins.settings.ProjectSettingsPlugin"
        }
        register("FoojayToolchainsSettingsPlugin") {
            id = "build-foojay-toolchains"
            implementationClass = "build.gradle.plugins.settings.FoojayToolchainsSettingsPlugin"
        }
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}
