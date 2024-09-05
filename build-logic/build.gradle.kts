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
    implementation(libraries.dokka.gradle.plugin)
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
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}
