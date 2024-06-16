package build.gradle.dsl

import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions

public fun KotlinCommonCompilerOptions.withCompilerArguments(configure: KotlinCommonCompilerOptionsBuilder.() -> Unit) {
    val arguments = KotlinCommonCompilerOptionsBuilder().apply(configure).build()

    freeCompilerArgs.addAll(arguments)
}
