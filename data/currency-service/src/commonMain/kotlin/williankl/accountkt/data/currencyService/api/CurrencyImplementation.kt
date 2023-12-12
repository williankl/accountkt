package williankl.accountkt.data.currencyService.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendEncodedPathSegments
import io.ktor.http.parameters
import williankl.accountkt.data.currencyService.CurrencyService
import williankl.accountkt.data.currencyService.models.AvailableSymbolsResponse
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyService.models.SymbolRateResponse

internal class CurrencyImplementation(
    private val httpClient: HttpClient,
) : CurrencyService {
    override suspend fun validSymbols(): Map<Symbol, SymbolName> {
        return httpClient
            .get(CurrencyEndpointConstants.SUPPORTED_SYMBOLS)
            .body<AvailableSymbolsResponse>()
            .supportedCodes.associate { codeList ->
                codeList[0] to codeList[1]
            }
    }

    override suspend fun retrieveRates(
        symbol: Symbol,
    ): SymbolRate {
        val response = httpClient
            .get(CurrencyEndpointConstants.LATEST_RATES) {
                url.appendEncodedPathSegments(symbol)
            }
            .body<SymbolRateResponse>()

        return SymbolRate(
            base = response.base,
            rates = response.rates,
            nextUpdateAt = response.nextUpdate,
        )
    }

}