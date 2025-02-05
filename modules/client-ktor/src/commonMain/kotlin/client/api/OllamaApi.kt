package client.api

import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbedApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.MonitoringApi
import org.nirmato.ollama.api.VersionApi

public interface OllamaApi : ChatApi, CompletionsApi, EmbedApi, ModelsApi, MonitoringApi, VersionApi
