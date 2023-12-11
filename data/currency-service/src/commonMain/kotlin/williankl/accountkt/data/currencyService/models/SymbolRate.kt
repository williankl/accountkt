package williankl.accountkt.data.currencyService.models

public data class SymbolRate (
    val timestamp: Long,
    val base: Symbol,
    val rates: Map<Symbol, Double>,
)