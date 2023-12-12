package williankl.accountkt.data.currencyService.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SymbolRateResponse(
    @SerialName("time_last_update_unix") val lastUpdated: Long,
    @SerialName("time_next_update_unix") val nextUpdate: Long,
    @SerialName("base_code") val base: Symbol,
    @SerialName("conversion_rates") val rates: Map<Symbol, Double>,
)