package williankl.accountkt.app.android.ui.currency

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import williankl.accountkt.data.currencyService.CurrencyService
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.CurrencyDataStorage
import williankl.accountkt.data.currencyStorage.CurrencyRateStorage

internal class CurrencyDisplayViewModel(
    private val currencyService: CurrencyService,
    private val currencyRateStorage: CurrencyRateStorage,
    private val currencyDataStorage: CurrencyDataStorage,
) : ScreenModel {

    private val _presentation = MutableStateFlow(CurrencyDisplayPresentation())
    val presentation: StateFlow<CurrencyDisplayPresentation> = _presentation

    data class CurrencyDisplayPresentation(
        val symbolRate: SymbolRate? = null,
        val symbolData: Map<Symbol, SymbolName> = emptyMap()
    )

    fun retrieveAllInfoForSymbol(symbol: Symbol) {
        coroutineScope.launch {
            try {
                val availableCurrencies = retrieveOrUpdateCurrencyDataList()
                val rate = retrieveOrUpdateCurrencyRates(symbol)

                _presentation.update {
                    CurrencyDisplayPresentation(
                        symbolRate = rate,
                        symbolData = availableCurrencies
                    )
                }
            } catch (error: Throwable) {
                error.printStackTrace()
            }
        }
    }

    private suspend fun retrieveOrUpdateCurrencyDataList(): Map<Symbol, SymbolName> {
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
                    currencyRateStorage.dropInfoForSymbol(symbol)
                    currencyRateStorage.insertSymbolRate(rate)
                }
    }

}