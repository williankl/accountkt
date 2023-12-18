package williankl.accountkt.app.android.ui.currency

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.feature.currencyFeature.CurrencyDataRetriever
import williankl.accountkt.feature.currencyFeature.models.CurrencyData
import williankl.accountkt.feature.sharedPreferences.models.CurrencyPreferences
import williankl.accountkt.feature.sharedPreferences.services.CurrencyPreferencesService

internal class CurrencyDisplayViewModel(
    private val currencyDataRetriever: CurrencyDataRetriever,
    private val currencyPreferencesService: CurrencyPreferencesService,
) : ScreenModel {

    private val _presentation = MutableStateFlow(CurrencyDisplayPresentation())
    val presentation: StateFlow<CurrencyDisplayPresentation> = _presentation

    data class CurrencyDisplayPresentation(
        val currencyData: CurrencyData? = null,
    )

    fun retrieveAllInfoForSymbol(symbol: Symbol) {
        coroutineScope.launch {
            try {
                _presentation.update {
                    CurrencyDisplayPresentation(
                        currencyData = currencyDataRetriever.currencyDataForSymbol(symbol),
                    )
                }
            } catch (error: Throwable) {
                error.printStackTrace()
            }
        }
    }

    fun saveState(currencyStateHandler: ConverterStateHandler) {
        currencyPreferencesService.updateCurrencyPreferences {
            with(currencyStateHandler) {
                CurrencyPreferences(
                    preferredSymbol = symbol,
                    defaultAmount = ratio,
                )
            }
        }
    }

    fun currencyPreferencesOrDefault(): CurrencyPreferences {
        return currencyPreferencesService.currencyPreferences
            ?: CurrencyPreferences(
                preferredSymbol = "BRL",
                defaultAmount = 1f,
            )
    }

    fun toggleFavourite(
        symbol: Symbol,
        setTo: Boolean,
    ) {
        coroutineScope.launch {
            try {
                currencyDataRetriever.updateFavouriteFor(symbol, setTo)
                _presentation.update { presentation ->
                    presentation.currencyData?.symbol
                        ?.let { baseSymbol ->
                            CurrencyDisplayPresentation(
                                currencyData = currencyDataRetriever.currencyDataForSymbol(
                                    baseSymbol
                                ),
                            )
                        }
                        ?: error("No base symbol found")
                }
            } catch (error: Throwable) {
                error.printStackTrace()
            }
        }
    }

}