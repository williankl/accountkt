package williankl.accountkt.ui.application.DI

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.ui.application.currency.CurrencyDisplayViewModel

internal val internalApplicationUiDI: DI.Module =
    DI.Module("williankl.accountkt.ui.application.internal") {
        bindSingleton {
            CurrencyDisplayViewModel(
                currencyDataRetriever = instance(),
                currencyPreferencesService = instance(),
            )
        }
    }