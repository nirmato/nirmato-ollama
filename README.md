# nirmato-ollama

[![Klibs](https://img.shields.io/badge/klibs-0.1.1-%237F52FF.svg?logo=kotlin&color=blue)](https://klibs.io/project/nirmato/nirmato-ollama)
![![kotlin](https://kotlinlang.org/)](https://img.shields.io/badge/kotlin--multiplatform-2.1.21-blue.svg?logo=kotlin)
![![License](https://github.com/nirmato/nirmato-ollama/blob/main/LICENSE.md)](https://img.shields.io/github/license/nirmato/nirmato-ollama)

Ollama API client for Kotlin multiplatform.

The implementation follows the OpenAPI definition [Ollama API](oas/ollama-openapi.yaml) described in [Ollama API Docs](https://github.com/ollama/ollama/blob/main/docs/api.md).

<a href="https://www.buymeacoffee.com/kkadete" target="_blank">
    <img src="https://cdn.buymeacoffee.com/buttons/v2/arial-yellow.png" alt="Buy Me A Coffee" style="width: 128px;" >
</a>

## Usage

Add the dependency to your Gradle configuration:

```kotlin
implementation("org.nirmato.ollama:nirmato-ollama-client-ktor:0.1.1")

// example using ktor CIO engine
implementation("io.ktor:ktor-client-cio:3.1.3")
```

or to your Maven pom:

```xml
<dependency>
    <groupId>org.nirmato.ollama</groupId>
    <artifactId>nirmato-ollama-client-ktor</artifactId>
    <version>0.1.1</version>
</dependency>

<!-- example using ktor CIO engine -->
<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-client-cio</artifactId>
    <version>3.1.3</version>
</dependency>
```

Alternatively, you can [choose](publishing/bom/README.md) individual components of this library.

An example of calling the Ollama client:

```kotlin
val ollamaClient = OllamaClient(CIO)

val request = chatRequest {
    model("tinyllama")
    messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
}

val response = ollamaClient.chat(request)
```

## Requirements

- Kotlin 1.8 or later
- JVM 11 or later

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Acknowledgements

JetBrains for making [Kotlin](https://kotlinlang.org).

## License

The source code is distributed under [Apache License 2.0](LICENSE.md).
