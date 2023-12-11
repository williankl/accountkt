package williankl.accountkt.data.currencyService

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.data.currencyService.api.CurrencyEndpointConstants
import williankl.accountkt.data.currencyService.api.CurrencyImplementation
import williankl.accountkt.data.currencyService.networking.ClientProvider
import williankl.accountkt.data.currencyService.networking.ClientSetupHandler

public val currencyServiceDI: DI.Module =
    DI.Module("williankl.accountkt.data.currencyService") {
        bindSingleton<CurrencyService> {
            CurrencyImplementation(
                httpClient = instance()
            )
        }

        bindSingleton {
            ClientProvider(
                clientSetupHandler = instance()
            ).provide()
        }

        bindSingleton {
            ClientSetupHandler(
                baseUrl = CurrencyEndpointConstants.BASE_URL,
                apiKey = BuildKonfig.apiKey,
                json = instance(),
            )
        }
    }