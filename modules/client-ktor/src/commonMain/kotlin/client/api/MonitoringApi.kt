package org.nirmato.ollama.api

public interface MonitoringApi {
    public suspend fun getMonitoring(): MonitoringResponse
}
