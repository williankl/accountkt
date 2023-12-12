package williankl.accountkt.app.android.ui.currency

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import williankl.accountkt.data.currencyService.CurrencyService
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolRate
import williankl.accountkt.data.currencyStorage.CurrencyStorage

internal class CurrencyDisplayViewModel(
    private val currencyService: CurrencyService,
    private val currencyStorage: CurrencyStorage,
) : ScreenModel {

    private val _presentation = MutableStateFlow(CurrencyDisplayPresentation())
    val presentation: StateFlow<CurrencyDisplayPresentation> = _presentation

    data class CurrencyDisplayPresentation(
        val symbolRate: SymbolRate? = null,
    )

    fun retrieveAllInfoForSymbol(symbol: Symbol) {
        coroutineScope.launch {
            try {
                val rate = currencyStorage.rateForSymbol(symbol)
                    ?: currencyService.retrieveRates(symbol)
                        .also { rate ->
                            currencyStorage.dropInfoForSymbol(symbol)
                            currencyStorage.insertSymbolRate(rate)
                        }

                _presentation.update {
                    CurrencyDisplayPresentation(rate)
                }
            } catch (error: Throwable) {
                error.printStackTrace()
            }
        }
    }

}