# nirmato-ollama

![![License]()](https://img.shields.io/badge/kotlin--multiplatform-2.0.10-blue.svg?logo=kotlin) ![![License](https://github.com/nirmato/nirmato-ollama/blob/main/LICENSE.md)](https://img.shields.io/github/license/nirmato/nirmato-ollama)

![badge][badge-jvm] ![badge][badge-js] ![badge][badge-nodejs] ![badge][badge-wasm-js]

[badge-android]: http://img.shields.io/badge/-android-6EDB8D.svg?style=flat
[badge-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg?style=flat
[badge-js]: http://img.shields.io/badge/-js-F8DB5D.svg?style=flat
[badge-nodejs]: https://img.shields.io/badge/-nodejs-68a063.svg?style=flat
[badge-wasm-js]: https://img.shields.io/badge/-wasm--js-306850.svg?style=flat
[badge-wasm]: https://img.shields.io/badge/-wasm-624FE8.svg?style=flat
[badge-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg?style=flat
[badge-windows]: http://img.shields.io/badge/-windows-4D76CD.svg?style=flat
[badge-apple-silicon]: http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg?style=flat
[badge-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat
[badge-mac]: http://img.shields.io/badge/-macos-111111.svg?style=flat
[badge-watchos]: http://img.shields.io/badge/-watchos-C0C0C0.svg?style=flat
[badge-tvos]: http://img.shields.io/badge/-tvos-808080.svg?style=flat

## Overview

Nirmato Ollama is a Kotlin multiplatform client to interact with Ollama API.

The implementation follows the OpenAPI definition [Ollama API](modules/nirmato-ollama-client/src/commonMain/oas/openapi.yaml) described in [Ollama API Docs](https://github.com/ollama/ollama/blob/main/docs/api.md).

> [!WARNING]
> nirmato-ollama is under development.
>
> Report any issue or bug <a href="/issues">in the GitHub repository.</a>
> 

## How to use

### Generate a completion

```kotlin
val ollama = createOllamaClient {
    logging = LoggingConfig(logLevel = LogLevel.All)
    timeout = TimeoutConfig(socket = 30.seconds)
    host = OllamaHost.Local
    retry = RetryStrategy(0)
    engine = CIO.create()
}

val completionRequest = generateCompletionRequest {
    model = "tinyllama"
    prompt = "Why is the sky blue?"
}
val response = ollama.generateCompletion(completionRequest)
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
    <artifactId>nirmato-ollama-client</artifactId>
    <version>VERSION</version>
</dependency>
```

## Contributions

Please feel free to submit a pull request. Contributions are welcome!

## Acknowledgements

JetBrains for making [Kotlin](https://kotlinlang.org).

## License

The source code is distributed under [Apache License 2.0](LICENSE.md).
