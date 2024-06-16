package build.gradle.api

import org.gradle.api.initialization.Settings

public fun Settings.includeModule(name: String) {
    require(name.isNotBlank())

    include(name)
    project(":${name}").projectDir = rootDir.resolve("modules/$name")
}
