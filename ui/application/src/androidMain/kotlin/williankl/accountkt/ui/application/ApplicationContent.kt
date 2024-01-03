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
import williankl.accountkt.ui.application.screens.currency.ConverterStateHandler.Companion.LocalConverterStateHandler
import williankl.accountkt.ui.application.screens.currency.ConverterStateHandler.Companion.rememberConverterStateHandler
import williankl.accountkt.ui.application.screens.currency.CurrencyDisplayScreen

@Composable
public fun ApplicationContent() {
    CommonApplicationContent()
}