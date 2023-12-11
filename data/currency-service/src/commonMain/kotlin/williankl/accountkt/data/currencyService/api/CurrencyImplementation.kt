package williankl.accountkt.data.currencyService.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.parameters
import williankl.accountkt.data.currencyService.CurrencyService
import williankl.accountkt.data.currencyService.models.AvailableSymbolsResponse
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate

internal class CurrencyImplementation(
    private val httpClient: HttpClient,
) : CurrencyService {
    override suspend fun validSymbols(): Map<Symbol, SymbolName> {
        return httpClient
            .get(CurrencyEndpointConstants.SUPPORTED_SYMBOLS)
            .body<AvailableSymbolsResponse>()
            .symbols
    }

    override suspend fun retrieveRates(
        symbol: Symbol,
        vararg forSymbols: Symbol,
    ): SymbolRate {
        return httpClient
            .get(CurrencyEndpointConstants.LATEST_RATES) {
                parameters {
                    append(CurrencyParameterConstants.BASE, symbol)
                    if (forSymbols.isNotEmpty()) {
                        append(
                            name = CurrencyParameterConstants.SYMBOLS,
                            value = forSymbols.joinToString(separator = ","),
                        )
                    }
                }
            }
            .body<SymbolRate>()
    }

}