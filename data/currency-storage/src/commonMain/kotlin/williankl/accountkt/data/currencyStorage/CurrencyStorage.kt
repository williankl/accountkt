package williankl.accountkt.data.currencyStorage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolRate

public interface CurrencyStorage {
    public suspend fun selectAllSymbolsRates(): List<SymbolRate>
    public suspend fun rateForSymbol(symbol: Symbol): SymbolRate?
    public suspend fun insertSymbolRate(symbolRate: SymbolRate)
    public suspend fun dropInfoForSymbol(symbol: Symbol)
    public suspend fun dropAll(symbol: Symbol)
}