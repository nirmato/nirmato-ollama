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
dependencies {
    implementation("org.nirmato.ollama:nirmato-ollama-client:0.1.0")

    // example using ktor CIO engine
    implementation("io.ktor:ktor-client-cio:3.0.3")
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.nirmato.ollama</groupId>
        <artifactId>nirmato-ollama-client</artifactId>
        <version>0.1.0</version>
    </dependency>
    
    <!-- example using ktor CIO engine -->
    <dependency>
        <groupId>io.ktor</groupId>
        <artifactId>ktor-client-cio</artifactId>
        <version>3.0.3</version>
    </dependency>
</dependencies>
```

Alternatively, you can [choose](publishing/bom/README.md) individual components of this library.

### Usage

```kotlin
val ollamaClient = OllamaClient(CIO)

val request = completionRequest {
    model = "tinyllama"
    prompt = "Why is the sky blue?"
}

val response = ollama.completion(request)
```

## Acknowledgements

JetBrains for making [Kotlin](https://kotlinlang.org).

## License

The source code is distributed under [Apache License 2.0](LICENSE.md).
