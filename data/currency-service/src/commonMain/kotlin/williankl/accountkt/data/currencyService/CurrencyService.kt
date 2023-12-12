package williankl.accountkt.data.currencyService

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate

public interface CurrencyService {
    public suspend fun validSymbols(): Map<Symbol, SymbolName>
    public suspend fun retrieveRates(symbol: Symbol): SymbolRate
}