package williankl.accountkt.data.currencyStorage.storage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.CurrencyDatabase
import williankl.accountkt.data.currencyStorage.CurrencyFavouriteStorage
import williankl.accountkt.data.currencyStorage.CurrencyRateStorage

internal class CurrencyFavouriteStorageImplementation(
    private val currencyDatabase: CurrencyDatabase,
) : CurrencyFavouriteStorage {

    init {
        currencyDatabase.databaseSymbolFavouriteQueries.createTableIfNeeded()
    }

    override suspend fun isFavourite(symbol: Symbol): Boolean {
        return currencyDatabase.databaseSymbolFavouriteQueries
            .retrieveItem(symbol)
            .executeAsOneOrNull()
            ?.let { it.is_favourite == 1L }
            ?: false
    }

    override suspend fun toggleFavourite(
        symbol: Symbol,
        setTo: Boolean,
    ) {
        with(currencyDatabase.databaseSymbolFavouriteQueries) {
            val dbValue = if (setTo) 1L else 0L
            retrieveItem(symbol).executeAsOneOrNull()
                ?.let {
                    updateIsFavourite(
                        setTo = dbValue,
                        symbol = symbol,
                    )
                }
                ?: insertItem(symbol, dbValue)
        }

    }

}