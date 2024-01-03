package williankl.accountkt.ui.application.DI

import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import williankl.accountkt.data.currencyService.currencyServiceDI
import williankl.accountkt.data.currencyStorage.currencyRateStorageDI
import williankl.accountkt.feature.currencyFeature.currencyFeatureStorageDI
import williankl.accountkt.feature.sharedPreferences.sharedPreferencesDI

public val applicationDI: DI.Module =
    DI.Module("williankl.accountkt.ui.application") {
        import(currencyServiceDI)
        import(currencyRateStorageDI)
        import(currencyFeatureStorageDI)
        import(sharedPreferencesDI)
        import(internalApplicationUiDI)
        import(platformDI())

        bindSingleton {
            Json {
                ignoreUnknownKeys = true
            }
        }
    }