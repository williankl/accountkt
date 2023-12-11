package williankl.accountkt.data.currencyService.networking

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

internal expect class ClientProvider(clientSetupHandler: ClientSetupHandler) {
    fun provide(): HttpClient
}