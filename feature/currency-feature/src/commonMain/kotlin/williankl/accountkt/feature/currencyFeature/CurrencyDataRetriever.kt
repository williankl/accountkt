package williankl.accountkt.feature.currencyFeature

import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.feature.currencyFeature.models.CurrencyData

public interface CurrencyDataRetriever {
    public suspend fun currencyDataForSymbol(symbol: Symbol): CurrencyData
}