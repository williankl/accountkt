package williankl.accountkt.data.currencyService.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AvailableSymbolsResponse(
    @SerialName("supported_codes") val supportedCodes: List<List<String>>,
)