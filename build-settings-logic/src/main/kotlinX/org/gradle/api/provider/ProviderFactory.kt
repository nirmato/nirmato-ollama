package org.gradle.api.provider

public fun ProviderFactory.gradleStringProperty(name: String): Provider<String> = gradleProperty(name)

public fun ProviderFactory.gradleBooleanProperty(name: String): Provider<Boolean> = gradleProperty(name).map { it.toBoolean() }.orElse(false)
