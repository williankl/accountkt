package williankl.accountkt.data.currencyStorage

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.data.currencyStorage.storage.CurrencyDataStorageImplementation
import williankl.accountkt.data.currencyStorage.storage.CurrencyFavouriteStorageImplementation
import williankl.accountkt.data.currencyStorage.storage.CurrencyRateStorageImplementation

internal expect fun platformCurrencyStorageDI(): DI.Module

public val currencyRateStorageDI: DI.Module =
    DI.Module("williankl.accountkt.data.currencyStorage") {
        import(platformCurrencyStorageDI())

        bindSingleton {
            CurrencyDatabase(
                driver = instance()
            )
        }

        bindSingleton<CurrencyRateStorage> {
            CurrencyRateStorageImplementation(
                currencyDatabase = instance(),
            )
        }

        bindSingleton<CurrencyDataStorage> {
            CurrencyDataStorageImplementation(
                currencyDatabase = instance(),
            )
        }

        bindSingleton<CurrencyFavouriteStorage> {
            CurrencyFavouriteStorageImplementation(
                currencyDatabase = instance(),
            )
        }
    }