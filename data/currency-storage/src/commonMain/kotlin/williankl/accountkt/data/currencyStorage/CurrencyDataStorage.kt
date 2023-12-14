package williankl.accountkt.data.currencyStorage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate

public interface CurrencyDataStorage {
    public suspend fun selectAllSymbolsData(): Map<Symbol, SymbolName>
    public suspend fun insertSymbolData(
        symbol: Symbol,
        name: SymbolName,
    )
    public suspend fun dropDataForSymbol(symbol: Symbol)
    public suspend fun dropAll()
}