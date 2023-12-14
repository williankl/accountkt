package williankl.accountkt.feature.currencyFeature.models

import williankl.accountkt.data.currencyService.models.SymbolName

public data class CurrencyData(
    val name: SymbolName,
    val symbol: SymbolName,
    val rates: List<CurrencyRate>,
)