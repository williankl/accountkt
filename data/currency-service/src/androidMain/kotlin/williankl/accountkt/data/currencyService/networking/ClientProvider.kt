package williankl.accountkt.data.currencyService.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual class ClientProvider actual constructor(
    private val clientSetupHandler: ClientSetupHandler,
) {
    actual fun provide(): HttpClient {
        return HttpClient(OkHttp) {
            with(clientSetupHandler) {
                configure()
            }
        }
    }
}

