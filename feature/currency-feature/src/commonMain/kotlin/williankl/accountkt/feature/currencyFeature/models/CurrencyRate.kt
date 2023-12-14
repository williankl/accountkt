package williankl.accountkt.feature.currencyFeature.models

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate

public data class CurrencyRate(
    val isFavourite: Boolean,
    val name: SymbolName,
    val symbol: Symbol,
    val rate: Double,
)