package williankl.accountkt.data.currencyStorage

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.data.currencyStorage.storage.CurrencyStorageImplementation

internal expect fun platformCurrencyStorageDI(): DI.Module

public val currencyStorageDI: DI.Module =
    DI.Module("williankl.accountkt.data.currencyStorage") {
        import(platformCurrencyStorageDI())

        bindSingleton {
            CurrencyDatabase(
                driver = instance()
            )
        }

        bindSingleton<CurrencyStorage> {
            CurrencyStorageImplementation(
                currencyDatabase = instance(),
            )
        }
    }