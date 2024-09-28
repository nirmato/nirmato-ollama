package org.nirmato.ollama.client

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Ollama client configuration.
 */
public class OllamaConfig(
    /** Logging configuration */
    public val logging: LoggingConfig = LoggingConfig(),
    /** HTTP client timeout */
    public val timeout: TimeoutConfig = TimeoutConfig(socket = 30.seconds),
    /** Extra http headers */
    public val headers: Map<String, String> = mutableMapOf(),
    /** Host configuration. */
    public val host: OllamaHost = OllamaHost.Local,
    /** HTTP proxy url. */
    public val proxy: ProxyConfig? = null,
    /** Rate limit retry configuration */
    public val retry: RetryStrategy = RetryStrategy(),
)

/**
 * A class to configure the Ollama host.
 * It provides a mechanism to customize the base URL and additional query parameters used in Ollama API requests.
 */
public class OllamaHost(

    /**
     * Base URL configuration.
     * This is the root URL that will be used for all API requests to Ollama.
     * The URL can include a base path, but in that case, the base path should always end with a `/`.
     * For example, a valid base URL would be "https://api.Ollama.com/v1/"
     */
    public val baseUrl: String,
) {

    public companion object {
        /**
         * A pre-configured instance of [OllamaHost] with the base URL set as `https://api.Ollama.com/v1/`.
         */
        public val Local: OllamaHost = OllamaHost(baseUrl = "http://localhost:11434/api/")

        /**
         * Creates an instance of [OllamaHost] configured for Azure hosting with the given resource name, deployment ID,
         * and API version.
         *
         * @param baseUrl The base url of the api.
         */
        public fun http(baseUrl: String): OllamaHost = OllamaHost(
            baseUrl = baseUrl,
        )
    }
}

/** Proxy configuration. */
public sealed interface ProxyConfig {

    /** Creates an HTTP proxy from [url]. */
    public class Http(public val url: String) : ProxyConfig

    /** Create socks proxy from [host] and [port]. */
    public class Socks(public val host: String, public val port: Int) : ProxyConfig
}

/**
 * Specifies the retry strategy
 *
 * @param maxRetries the maximum amount of retries to perform for a request
 * @param base retry base value
 * @param maxDelay max retry delay
 */
public class RetryStrategy(
    public val maxRetries: Int = 3,
    public val base: Double = 2.0,
    public val maxDelay: Duration = 60.seconds,
)

/**
 * Defines the configuration parameters for logging.
 *
 * @property logLevel the level of logging to be used by the HTTP client.
 * @property logger the logger instance to be used by the HTTP client.
 * @property sanitize flag indicating whether to sanitize sensitive information (i.e., authorization header) in the logs
 */
public class LoggingConfig(
    public val logLevel: LogLevel = LogLevel.Headers,
    public val logger: Logger = Logger.Simple,
    public val sanitize: Boolean = true,
)

/**
 * Http client logging log level.
 */
public enum class LogLevel {
    All,
    Headers,
    Body,
    Info,
    None,
}

/**
 * Http client logger.
 */
public fun interface Logger {
    /**
     * Add [message] to log.
     */
    public fun log(message: String)

    public companion object {

        /**
         * [Logger] using [println].
         */
        public val Simple: Logger get() = Logger { println("HttpClient: $it") }

        /**
         * Empty [Logger].
         */
        public val Empty: Logger get() = Logger { /* No-op */ }
    }
}

/**
 * Http operations timeouts.
 *
 * @param request time period required to process an HTTP call: from sending a request to receiving a response
 * @param connect time period in which a client should establish a connection with a server
 * @param socket maximum time of inactivity between two data packets when exchanging data with a server
 */
public class TimeoutConfig(
    public val request: Duration? = null,
    public val connect: Duration? = null,
    public val socket: Duration? = null,
)

/**
 * Builder for [OllamaConfig].
 */
@OllamaDsl
public class OllamaConfigBuilder {
    public var logging: LoggingConfig = LoggingConfig()
    public var timeout: TimeoutConfig = TimeoutConfig(socket = 30.seconds)
    public var headers: Map<String, String> = mutableMapOf()
    public var host: OllamaHost = OllamaHost.Local
    public var proxy: ProxyConfig? = null
    public var retry: RetryStrategy = RetryStrategy()

    public fun build(): OllamaConfig = OllamaConfig(
        logging = logging,
        timeout = timeout,
        headers = headers,
        host = host,
        proxy = proxy,
        retry = retry,
    )
}
