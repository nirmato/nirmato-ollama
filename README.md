# nirmato-ollama

![![kotlin](https://kotlinlang.org/)](https://img.shields.io/badge/kotlin--multiplatform-2.1.20-blue.svg?logo=kotlin) ![![License](https://github.com/nirmato/nirmato-ollama/blob/main/LICENSE.md)](https://img.shields.io/github/license/nirmato/nirmato-ollama)

Unofficial Ollama API client for Kotlin multiplatform.

The implementation follows the OpenAPI definition [Ollama API](modules/oas/ollama-openapi.yaml) described in [Ollama API Docs](https://github.com/ollama/ollama/blob/main/docs/api.md).

> [!WARNING]
> nirmato-ollama is under development.
>
> Report any issue or bug <a href="https://github.com/nirmato/nirmato-ollama/issues">in the GitHub repository.</a>
>

## Dependency

Add the dependency to your project:

> [!WARNING]
> nirmato-ollama is not yet available in a repository.
>

### Gradle

```kotlin
implementation("org.nirmato.ollama:nirmato-ollama-api:0.1.0")

// using ktor client implementation
implementation("org.nirmato.ollama:nirmato-ollama-ktor:0.1.0")
```

### Maven

```xml
<dependency>
    <groupId>org.nirmato.ollama</groupId>
    <artifactId>nirmato-ollama-api</artifactId>
    <version>0.1.0</version>
</dependency>

<!-- using ktor client implementation -->
<dependency>
    <groupId>org.nirmato.ollama</groupId>
    <artifactId>nirmato-ollama-ktor</artifactId>
    <version>0.1.0</version>
</dependency>
```

Alternatively, you can [choose](publishing/bom/README.md) individual components of this library.

### Usage

```kotlin
val ollamaConfig = OllamaConfigBuilder().apply {
    logging = LoggingConfig(logLevel = LogLevel.All)
    timeout = TimeoutConfig(socket = 30.seconds)
    host = OllamaHost.Local
    retry = RetryStrategy(0)
}.build()

val httpClientProvider = DefaultHttpClientProvider(Java.create(), ollamaConfig)
val ollamaClient = OllamaClient(httpClientProvider)

val response = ollama.completion {
    model = "tinyllama"
    prompt = "Why is the sky blue?"
}
```

## Acknowledgements

JetBrains for making [Kotlin](https://kotlinlang.org).

## License

The source code is distributed under [Apache License 2.0](LICENSE.md).
