package org.gradle.api.initialization

public fun Settings.includeModule(name: String): Unit = includeProject("modules", name)

public fun Settings.includeSample(name: String): Unit = includeProject("samples", name)

private fun Settings.includeProject(directory: String, name: String) {
    require(name.isNotBlank())

    include(name)

    project(":${name}").projectDir = rootDir.resolve("$directory/$name")
}
