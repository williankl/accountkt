package williankl.accountkt.data.currencyStorage.storage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.CurrencyDataStorage
import williankl.accountkt.data.currencyStorage.CurrencyDatabase
import williankl.accountkt.data.currencyStorage.CurrencyRateStorage

internal class CurrencyDataStorageImplementation(
    private val currencyDatabase: CurrencyDatabase,
) : CurrencyDataStorage {

    init {
        currencyDatabase.databaseSymbolDataQueries.createTableIfNeeded()
    }

    override suspend fun selectAllSymbolsData(): Map<Symbol, SymbolName> {
        return currencyDatabase.databaseSymbolDataQueries
            .selectAll()
            .executeAsList()
            .associate { databaseSymbolData ->
                databaseSymbolData.base to databaseSymbolData.name
            }
    }

    override suspend fun insertSymbolRate(
        symbol: Symbol,
        name: SymbolName,
    ) {
        currencyDatabase.databaseSymbolDataQueries
            .insert(symbol, name)
    }

    override suspend fun dropDataForSymbol(symbol: Symbol) {
        currencyDatabase.databaseSymbolDataQueries
            .dropForSymbol(symbol)
    }

    override suspend fun dropAll(symbol: Symbol) {
        currencyDatabase.databaseSymbolDataQueries
            .dropAll()
    }

}