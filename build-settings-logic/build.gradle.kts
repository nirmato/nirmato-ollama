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
        apiVersion = providers.gradleProperty("kotlin.compilerOptions.apiVersion").map(KotlinVersion::fromVersion)
        languageVersion = providers.gradleProperty("kotlin.compilerOptions.languageVersion").map(KotlinVersion::fromVersion)
        progressiveMode = true

        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }

    jvmToolchain {
        languageVersion = project.providers.provider {
            JavaLanguageVersion.of(getGradleProperty("kotlin.javaToolchain.mainJvmCompiler"))
        }
    }
}

gradlePlugin {
    plugins {
        register("DefaultSettingsPlugin") {
            id = "build-settings-default"
            implementationClass = "build.gradle.plugins.settings.DefaultSettingsPlugin"
        }
        register("FoojayConfigurerPlugin") {
            id = "build-foojay"
            implementationClass = "build.gradle.plugins.settings.FoojayBuildPlugin"
        }
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}

internal fun getGradleProperty(key: String, environmentKey: String? = null): String {
    val gradleValue = providers.gradleProperty(key).get().takeIf { value -> value.isNotBlank() }
    val systemValue = System.getProperty(key)?.takeIf { value -> value.isNotBlank() }
    val environmentValue = environmentKey?.let { System.getenv(it) }?.takeIf { value -> value.isNotBlank() }
    return environmentValue ?: systemValue ?: gradleValue ?: throw StopExecutionException("Property $key is not found")
}
