package williankl.accountkt.data.currencyService.api

import williankl.accountkt.data.currencyService.models.Symbol

public object CurrencyEndpointConstants {
    internal const val BASE_URL = "https://v6.exchangerate-api.com/v6/"
    internal const val SUPPORTED_SYMBOLS = "codes/"
    internal const val LATEST_RATES = "latest/"

    public fun currencyImageUrl(symbol: Symbol): String {
        return "https://wise.com/public-resources/assets/flags/rectangle/${symbol.lowercase()}.png"
    }
}