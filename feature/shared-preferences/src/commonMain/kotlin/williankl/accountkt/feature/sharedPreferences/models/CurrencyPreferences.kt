package williankl.accountkt.feature.sharedPreferences.models

import williankl.accountkt.data.currencyService.models.Symbol

public data class CurrencyPreferences(
    val preferredSymbol: Symbol,
    val defaultAmount: Double,
)