package williankl.accountkt.data.currencyStorage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolRate

public interface CurrencyRateStorage {
    public suspend fun selectAllSymbolsRates(): List<SymbolRate>
    public suspend fun rateForSymbol(symbol: Symbol): SymbolRate?
    public suspend fun updateSymbolRate(symbolRate: SymbolRate)
    public suspend fun dropInfoForSymbol(symbol: Symbol)
    public suspend fun dropAll()
}