# nirmato-ollama

[![Klibs](https://img.shields.io/badge/klibs-0.2.0-%237F52FF.svg?logo=kotlin&color=blue)](https://klibs.io/project/nirmato/nirmato-ollama)
![![kotlin](https://kotlinlang.org/)](https://img.shields.io/badge/kotlin--multiplatform-2.2.20-blue.svg?logo=kotlin)
![![License](https://github.com/nirmato/nirmato-ollama/blob/main/LICENSE.md)](https://img.shields.io/github/license/nirmato/nirmato-ollama)

An Ollama client SDK that allows you to easily interact with the Ollama API.

The supported API follows the OpenAPI definition [Ollama API](oas/ollama-openapi.yaml) described in [Ollama API Docs](https://github.com/ollama/ollama/blob/main/docs/api.md).

<a href="https://www.buymeacoffee.com/kkadete" target="_blank">
    <img src="https://cdn.buymeacoffee.com/buttons/v2/arial-yellow.png" alt="Buy Me A Coffee" style="width: 128px;" >
</a>

## Features

- **Comprehensive API Coverage:** Full support for the Ollama API, including chat, streaming, model management, and embeddings.
- **Kotlin Multiplatform:** Write once, run on any platform that Kotlin supports.
- **Asynchronous Operations:** Built with Kotlin Coroutines for non-blocking, asynchronous API calls.
- **Type-Safe DSL:** A clean and expressive DSL for building requests.
- **Streaming Support:** Handle large responses efficiently with built-in streaming capabilities.

## Requirements

- Kotlin 1.8 or higher
- JVM 11 or higher

## Installation

Add the dependency to your Gradle configuration:

```kotlin
implementation("org.nirmato.ollama:nirmato-ollama-client-ktor:0.2.0")

// example using ktor CIO engine
implementation("io.ktor:ktor-client-cio:3.1.3")
```

or to your Maven pom:

```xml
<dependency>
    <groupId>org.nirmato.ollama</groupId>
    <artifactId>nirmato-ollama-client-ktor</artifactId>
    <version>0.2.0</version>
</dependency>

<!-- example using ktor CIO engine -->
<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-client-cio</artifactId>
    <version>3.1.3</version>
</dependency>
```

## Usage

The `OllamaClient` class contains all the methods needed to interact with the Ollama API. An example of calling Ollama:

```kotlin
val ollamaClient = OllamaClient(CIO) {
    httpClient {
        // ktor HttpClient configurations
        defaultRequest {
            url("http://localhost:11434/api/")
        }
    }
}

val request = chatRequest {
    model("tinyllama")
    messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
}

val response = ollamaClient.chat(request)
```

See the [samples](samples) directory for complete examples.

## API Overview

The `OllamaApi` interface provides the following methods:

- `chat(chatRequest)`: Send a chat request and get a response.
- `chatStream(chatRequest)`: Stream the chat response.
- `generateEmbed(generateEmbedRequest)`: Generate embeddings for a given input.
- `createModel(createModelRequest)`: Create a new model.
- `pullModel(pullModelRequest)`: Pull a model from the registry.
- `pushModel(pushModelRequest)`: Push a model to the registry.
- `listModels()`: List all available models.
- ...and many more.

For a complete list of methods, please refer to the `OllamaApi` interface.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Acknowledgements

- [JetBrains](https://www.jetbrains.com/) for creating [Kotlin](https://kotlinlang.org/).

## License

The source code is distributed under the [Apache License 2.0](LICENSE.md).
