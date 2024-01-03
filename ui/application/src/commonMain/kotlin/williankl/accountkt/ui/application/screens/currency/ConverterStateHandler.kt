package williankl.accountkt.ui.application.screens.currency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.feature.currencyFeature.models.CurrencyRate

public class ConverterStateHandler(
    initSymbol: Symbol,
    initRatio: Float,
) {
    internal companion object {
        @Composable
        fun rememberConverterStateHandler(
            initSymbol: Symbol = "BRL",
            initRatio: Float = 1.0f,
        ): ConverterStateHandler = remember {
            ConverterStateHandler(initSymbol, initRatio)
        }

        val LocalConverterStateHandler: ProvidableCompositionLocal<ConverterStateHandler?> =
            staticCompositionLocalOf { null }
    }

    public var symbol: String by mutableStateOf(initSymbol)
        internal set

    public var ratio: Float by mutableFloatStateOf(initRatio)
        internal set

    public var supportedTargetSymbols: List<CurrencyRate> by mutableStateOf(emptyList())
        internal set
}