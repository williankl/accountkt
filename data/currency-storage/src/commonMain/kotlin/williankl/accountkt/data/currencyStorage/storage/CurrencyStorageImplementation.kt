package williankl.accountkt.data.currencyStorage.storage

import app.cash.sqldelight.db.SqlDriver
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.CurrencyDatabase
import williankl.accountkt.data.currencyStorage.CurrencyStorage
import williankl.accountkt.data.currencyStorage.DatabaseSymbolRate

internal class CurrencyStorageImplementation(
    private val currencyDatabase: CurrencyDatabase,
) : CurrencyStorage {

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

    override suspend fun insertSymbolRate(symbolRate: SymbolRate) {
        with(DatabaseMapper.mapToDatabaseModel(symbolRate)) {
            currencyDatabase.databaseSymbolRateQueries
                .insert(base, timestamp, rates)
        }
    }

    override suspend fun dropInfoForSymbol(symbol: Symbol) {
        currencyDatabase.databaseSymbolRateQueries
            .dropForSymbol(symbol)
    }

    override suspend fun dropAll(symbol: Symbol) {
        currencyDatabase.databaseSymbolRateQueries
            .dropAll()
    }

}