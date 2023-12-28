package williankl.accountkt.ui.application

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import williankl.accountkt.ui.application.currency.ConverterStateHandler.Companion.LocalConverterStateHandler
import williankl.accountkt.ui.application.currency.ConverterStateHandler.Companion.rememberConverterStateHandler
import williankl.accountkt.ui.application.currency.CurrencyDisplayScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun ApplicationContent() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalConverterStateHandler provides rememberConverterStateHandler()
        ) {
            BottomSheetNavigator(
                sheetBackgroundColor = Color.White,
                scrimColor = Color.Black.copy(alpha = 0.25f),
                sheetShape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
                content = {
                    Navigator(CurrencyDisplayScreen()) {
                        SlideTransition(it)
                    }
                }
            )
        }
    }
}