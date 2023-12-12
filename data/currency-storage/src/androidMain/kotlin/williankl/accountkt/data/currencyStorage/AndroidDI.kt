package williankl.accountkt.data.currencyStorage

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.data.currencyStorage.driver.DriverProvider

internal actual fun platformCurrencyStorageDI() =
    DI.Module("williankl.accountkt.data.currencyStorage.android") {
        bindSingleton {
            DriverProvider(
                context = instance(),
            ).provide()
        }
    }