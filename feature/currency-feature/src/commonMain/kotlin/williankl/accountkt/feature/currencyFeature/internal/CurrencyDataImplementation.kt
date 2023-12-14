package williankl.accountkt.feature.currencyFeature.internal

import williankl.accountkt.data.currencyService.CurrencyService
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.CurrencyDataStorage
import williankl.accountkt.data.currencyStorage.CurrencyFavouriteStorage
import williankl.accountkt.data.currencyStorage.CurrencyRateStorage
import williankl.accountkt.feature.currencyFeature.CurrencyDataRetriever
import williankl.accountkt.feature.currencyFeature.models.CurrencyData
import williankl.accountkt.feature.currencyFeature.models.CurrencyRate

internal class CurrencyDataImplementation(
    private val currencyService: CurrencyService,
    private val currencyDataStorage: CurrencyDataStorage,
    private val currencyRateStorage: CurrencyRateStorage,
    private val currencyFavouriteStorage: CurrencyFavouriteStorage,
) : CurrencyDataRetriever {
    override suspend fun currencyDataForSymbol(symbol: Symbol): CurrencyData {
        val validSymbols = retrieveOrUpdateValidSymbols()
        val symbolRates = retrieveOrUpdateCurrencyRates(symbol)

        return CurrencyData(
            symbol = symbol,
            name = validSymbols[symbol].orEmpty(),
            rates = validSymbols.mapNotNull { (rateSymbol, name) ->
                if (symbol == rateSymbol) null
                else CurrencyRate(
                    isFavourite = currencyFavouriteStorage.isFavourite(rateSymbol),
                    name = name,
                    symbol = rateSymbol,
                    rate = symbolRates.rates[rateSymbol]
                        ?: error("Value mapping not found for $symbol -> $rateSymbol"),
                )
            },
        )
    }

    override suspend fun updateFavouriteFor(
        symbol: Symbol,
        setTo: Boolean,
    ): CurrencyData {
        currencyFavouriteStorage.toggleFavourite(symbol, setTo)
        return currencyDataForSymbol(symbol)
    }

    private suspend fun retrieveOrUpdateValidSymbols(): Map<Symbol, SymbolName> {
        return currencyDataStorage.selectAllSymbolsData()
            .ifEmpty {
                currencyService.validSymbols()
                    .also { dataMap ->
                        currencyDataStorage.dropAll()
                        dataMap.forEach { (symbol, name) ->
                            currencyDataStorage.insertSymbolData(symbol, name)
                        }
                    }
            }
    }

    private suspend fun retrieveOrUpdateCurrencyRates(symbol: Symbol): SymbolRate {
        return currencyRateStorage.rateForSymbol(symbol)
            ?: currencyService.retrieveRates(symbol)
                .also { rate ->
                    currencyRateStorage.updateSymbolRate(rate)
                }
    }
}