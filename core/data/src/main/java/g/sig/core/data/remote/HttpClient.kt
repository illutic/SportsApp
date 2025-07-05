package g.sig.core.data.remote

import g.sig.core.data.serialization.JsonSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingFormat
import io.ktor.serialization.kotlinx.json.json

internal fun buildHttpClient(engine: HttpClientEngine) =
    HttpClient(engine) {
        expectSuccess = true
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.INFO
            format = LoggingFormat.Default
        }
        install(ContentNegotiation) {
            json(JsonSerializer)
        }
    }
