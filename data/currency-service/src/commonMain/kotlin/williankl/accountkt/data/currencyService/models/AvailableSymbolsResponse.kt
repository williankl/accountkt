package williankl.accountkt.data.currencyService.models

import kotlinx.serialization.Serializable

@Serializable
internal data class AvailableSymbolsResponse (
    val success: Boolean,
    val symbols: Map<Symbol, Symbol>,
)