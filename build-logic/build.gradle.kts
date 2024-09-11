import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

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
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${getKotlinPluginVersion()}")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:${getKotlinPluginVersion()}")
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
        register("MultiplatformPlugin") {
            id = "build-multiplatform"
            implementationClass = "build.gradle.plugins.build.MultiplatformPlugin"
        }
        register("AssemblerPlugin") {
            id = "build-assembler"
            implementationClass = "build.gradle.plugins.build.AssemblerPlugin"
        }
    }
}

tasks {
    withType<ValidatePlugins>().configureEach {
        failOnWarning.set(true)
        enableStricterValidation.set(true)
    }
}
