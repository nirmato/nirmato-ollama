import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.cio.CIO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.nirmato.ollama.api.CopyModelRequest.Companion.copyModelRequest
import org.nirmato.ollama.api.ModelInfoRequest.Companion.modelInfoRequest
import org.nirmato.ollama.client.LogLevel
import org.nirmato.ollama.client.LoggingConfig
import org.nirmato.ollama.client.OllamaHost
import org.nirmato.ollama.client.RetryStrategy
import org.nirmato.ollama.client.TimeoutConfig
import org.nirmato.ollama.createOllamaClient

class CopyTest {
    @Test
    fun copyAndGetAndShow_validRequest_returnSuccess() = runTest {
        val ollama = createOllamaClient {
            logging = LoggingConfig(logLevel = LogLevel.All)
            timeout = TimeoutConfig(socket = 30.seconds)
            host = OllamaHost.Local
            retry = RetryStrategy(0)
            engine = CIO.create()
        }

        val newModel = "test-tinyllama:latest"

        val copyModelRequest = copyModelRequest {
            source = "tinyllama"
            destination = newModel
        }

        ollama.copyModel(copyModelRequest)

        val model = ollama.listModels().models?.first { it.name == newModel }

        val modelInfoRequest = modelInfoRequest {
            name = model?.name
        }

        val modelInfo = ollama.showModelInfo(modelInfoRequest)

        assertEquals("llama", modelInfo.details?.family)
    }
}
