package williankl.accountkt.data.currencyService.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

internal actual class ClientProvider actual constructor(
    private val clientSetupHandler: ClientSetupHandler,
) {
    actual fun provide(): HttpClient {
        return HttpClient(Darwin) {
            with(clientSetupHandler) {
                configure()
            }
        }
    }
}

