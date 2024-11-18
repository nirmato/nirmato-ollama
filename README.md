# nirmato-ollama

![![kotlin](https://kotlinlang.org/)](https://img.shields.io/badge/kotlin--multiplatform-2.1.0-blue.svg?logo=kotlin) ![![License](https://github.com/nirmato/nirmato-ollama/blob/main/LICENSE.md)](https://img.shields.io/github/license/nirmato/nirmato-ollama)

Unofficial Ollama API client using Kotlin multiplatform.

The implementation follows the OpenAPI definition [Ollama API](modules/oas/ollama-openapi.yaml) described in [Ollama API Docs](https://github.com/ollama/ollama/blob/main/docs/api.md).

> [!WARNING]
> nirmato-ollama is under development.
>
> Report any issue or bug <a href="https://github.com/nirmato/nirmato-ollama/issues">in the GitHub repository.</a>
>

## Supported Platforms

| Target Platform | Target preset                                                                                                      |
|:---------------:|--------------------------------------------------------------------------------------------------------------------|
|   Kotlin/JVM    | <ul><li>`jvm`</li></ul>                                                                                            |
|  Kotlin/WasmJS  | <ul><li>`wasmJs`</li></ul>                                                                                         |
|    Kotlin/JS    | <ul><li>`js`</li></ul>                                                                                             |
|      Linux      | <ul><li>`linuxX64`</li><li>`linuxArm64`</li></ul>                                                                  |
|    MingwX64     | <ul><li>`mingwX64`</li></ul>                                                                                       |
|       iOS       | <ul><li>`iosX64`</li><li>`iosArm64`</li><li>`iosSimulatorArm64`</li></ul>                                          |
|      MacOS      | <ul><li>`macosX64`</li><li>`macosArm64`</li></ul>                                                                  |
|     WatchOS     | <ul><li>`watchosX64`</li><li>`watchosArm64`</li><li>`watchosDeviceArm64`</li><li>`watchosSimulatorArm64`</li></ul> |
|      tvOS       | <ul><li>`tvosX64`</li><li>`tvosArm64`</li><li>`tvosSimulatorArm64`</li></ul>                                       |

## Compatibility table with Ollama API

| Version | Ollama API |
|---------|------------|
| 1.0.x   | 0.3.3      |
| < 1.0.0 | 0.3.3      |

## How to use

### Generate a completion

```kotlin
val ollama = OllamaClient {
    logging = LoggingConfig(logLevel = LogLevel.All)
    timeout = TimeoutConfig(socket = 30.seconds)
    host = OllamaHost.Local
    retry = RetryStrategy(0)
    engine = CIO.create()
}

val response = ollama.completion {
    model = "tinyllama"
    prompt = "Why is the sky blue?"
}
```

## Getting Started

### Gradle

1. Add GitHub Maven Packages repository to your project's settings.gradle.kts

```kotlin
repositories {
    maven {
        name = "GitHub Apache Maven Packages"
        url = uri("https://maven.pkg.github.com/nirmato/nirmato-ollama")
    }
}
```

2. Add the dependency in your project `build.gradle.kt`
 
```kotlin
dependencies {
    implementation("org.nirmato.ollama:nirmato-ollama-client:VERSION")
}
```

### Maven

1. Add GitHub Maven Packages repository to your project's pom.xml or your `settings.xml`

```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Apache Maven Packages</name>
        <url>https://maven.pkg.github.com/nirmato/nirmato-ollama</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>
```

2. Add the dependency in your project in your Maven project

```xml
<dependency>
    <groupId>org.nirmato.ollama</groupId>
    <artifactId>nirmato-ollama-client-[jvm]</artifactId>
    <version>VERSION</version>
</dependency>
```

## Contributions

Please feel free to submit a pull request. Contributions are welcome!

## Acknowledgements

JetBrains for making [Kotlin](https://kotlinlang.org).

## License

The source code is distributed under [Apache License 2.0](LICENSE.md).
