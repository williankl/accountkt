package williankl.accountkt.data.currencyService.models

import kotlinx.serialization.Serializable

@Serializable
public data class SymbolRate (
    val timestamp: Long,
    val base: Symbol,
    val rates: Map<Symbol, Double>,
)