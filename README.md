# nirmato-ollama

![![kotlin](https://kotlinlang.org/)](https://img.shields.io/badge/kotlin--multiplatform-2.1.21-blue.svg?logo=kotlin)
[![Build Status](https://github.com/nirmato/nirmato-ollama/actions/workflows/ci-main.yml/badge.svg?branch=main)](https://github.com/nirmato/nirmato-ollama/actions/workflows/ci-main.yml)
![![License](https://github.com/nirmato/nirmato-ollama/blob/main/LICENSE.md)](https://img.shields.io/github/license/nirmato/nirmato-ollama)

Unofficial Ollama API client for Kotlin multiplatform.

The implementation follows the OpenAPI definition [Ollama API](oas/ollama-openapi.yaml) described in [Ollama API Docs](https://github.com/ollama/ollama/blob/main/docs/api.md).

<a href="https://www.buymeacoffee.com/kkadete" target="_blank">
    <img src="https://cdn.buymeacoffee.com/buttons/v2/arial-yellow.png" alt="Buy Me A Coffee" style="width: 128px;" >
</a>

## Dependency

Add the dependency to your project:

### Gradle

```kotlin
implementation("org.nirmato.ollama:nirmato-ollama-client-ktor:0.1.0")

// example using ktor CIO engine
implementation("io.ktor:ktor-client-cio:3.1.2")
```

### Maven

```xml
<dependency>
    <groupId>org.nirmato.ollama</groupId>
    <artifactId>nirmato-ollama-client-ktor</artifactId>
    <version>0.1.0</version>
</dependency>

<!-- example using ktor CIO engine -->
<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-client-cio</artifactId>
    <version>3.1.2</version>
</dependency>
```

Alternatively, you can [choose](publishing/bom/README.md) individual components of this library.

### Usage

```kotlin
val ollamaClient = OllamaClient(CIO)

val request = chatRequest {
    model("tinyllama")
    messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
}

val response = ollamaClient.chat(request)
```

## Acknowledgements

JetBrains for making [Kotlin](https://kotlinlang.org).

## License

The source code is distributed under [Apache License 2.0](LICENSE.md).
