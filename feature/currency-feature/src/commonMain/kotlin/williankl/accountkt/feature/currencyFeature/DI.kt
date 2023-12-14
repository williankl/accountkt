package williankl.accountkt.feature.currencyFeature

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.feature.currencyFeature.internal.CurrencyDataImplementation

public val currencyRateStorageDI: DI.Module =
    DI.Module("williankl.accountkt.data.currencyStorage") {
        bindSingleton<CurrencyDataRetriever> {
            CurrencyDataImplementation(
                currencyService = instance(),
                currencyDataStorage = instance(),
                currencyRateStorage = instance(),
            )
        }
    }