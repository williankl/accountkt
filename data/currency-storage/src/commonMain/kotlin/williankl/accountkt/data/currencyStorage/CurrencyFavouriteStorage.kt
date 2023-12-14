package williankl.accountkt.data.currencyStorage

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate

public interface CurrencyFavouriteStorage {
    public suspend fun isFavourite(symbol: Symbol): Boolean

    public suspend fun toggleFavourite(
        symbol: Symbol,
        setTo: Boolean,
    )
}