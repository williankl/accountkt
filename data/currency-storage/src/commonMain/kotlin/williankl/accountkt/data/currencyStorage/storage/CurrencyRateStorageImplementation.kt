package williankl.accountkt.data.currencyStorage.storage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.CurrencyDatabase
import williankl.accountkt.data.currencyStorage.CurrencyRateStorage

internal class CurrencyRateStorageImplementation(
    private val currencyDatabase: CurrencyDatabase,
) : CurrencyRateStorage {

    init {
        currencyDatabase.databaseSymbolRateQueries.createTableIfNeeded()
    }

    override suspend fun selectAllSymbolsRates(): List<SymbolRate> {
        return currencyDatabase.databaseSymbolRateQueries
            .selectAll()
            .executeAsList()
            .map(DatabaseMapper::mapToDomain)
    }

    override suspend fun rateForSymbol(symbol: Symbol): SymbolRate? {
        return currencyDatabase.databaseSymbolRateQueries
            .selectForBaseSymbol(symbol)
            .executeAsOneOrNull()
            ?.let(DatabaseMapper::mapToDomain)
    }

    override suspend fun updateSymbolRate(symbolRate: SymbolRate) {
        with(DatabaseMapper.mapToDatabaseModel(symbolRate)) {
            currencyDatabase.databaseSymbolRateQueries
                .selectForBaseSymbol(base)
                .executeAsOneOrNull()
                ?.run {
                    currencyDatabase.databaseSymbolRateQueries
                        .updateSymbolValue(rates, base)
                }
                ?: run {
                    currencyDatabase.databaseSymbolRateQueries
                        .insert(base, nextUpdateAt, rates)
                }
        }
    }

    override suspend fun dropInfoForSymbol(symbol: Symbol) {
        currencyDatabase.databaseSymbolRateQueries
            .dropForSymbol(symbol)
    }

    override suspend fun dropAll() {
        currencyDatabase.databaseSymbolRateQueries
            .dropAll()
    }

}