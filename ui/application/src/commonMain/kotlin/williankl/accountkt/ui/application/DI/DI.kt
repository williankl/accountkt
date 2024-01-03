package williankl.accountkt.ui.application.DI

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.ui.application.screens.currency.CurrencyDisplayViewModel
import williankl.accountkt.ui.application.screens.options.OptionsBottomSheetViewModel

internal val internalApplicationUiDI: DI.Module =
    DI.Module("williankl.accountkt.ui.application.internal") {
        bindSingleton {
            CurrencyDisplayViewModel(
                currencyDataRetriever = instance(),
                currencyPreferencesService = instance(),
            )
        }

        bindSingleton {
            OptionsBottomSheetViewModel(
                platformSharedActions = instance(),
            )
        }
    }