package williankl.accountkt.data.currencyService.networking

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.appendEncodedPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class ClientSetupHandler(
    private val apiKey: String,
    private val baseUrl: String,
    private val json: Json,
) {
    fun HttpClientConfig<*>.configure() {
        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(DefaultRequest) {
            url("$baseUrl$apiKey/")
        }
    }
}