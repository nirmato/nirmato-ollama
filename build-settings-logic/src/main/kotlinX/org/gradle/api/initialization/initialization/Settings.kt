package org.gradle.api.initialization

public fun Settings.includeModule(name: String) {
    require(name.isNotBlank())

    include(name)

    project(":${name}").projectDir = rootDir.resolve("modules/$name")
}
