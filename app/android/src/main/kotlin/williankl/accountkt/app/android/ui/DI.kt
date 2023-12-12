package williankl.accountkt.app.android.ui

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.app.android.ui.currency.CurrencyDisplayViewModel

public val applicationUiDI: DI.Module =
    DI.Module("williankl.accountkt.ui.application") {
        bindSingleton {
            CurrencyDisplayViewModel(
                currencyService = instance(),
                currencyRateStorage = instance(),
            )
        }
    }