package williankl.accountkt.ui.application.currency

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

internal class ConverterStateHandler(
    initSymbol: String,
    initRatio: Float,
) {
    var symbol by mutableStateOf(initSymbol)
    var ratio by mutableFloatStateOf(initRatio)
}