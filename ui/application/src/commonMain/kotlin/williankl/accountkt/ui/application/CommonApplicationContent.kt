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
import org.kodein.di.compose.withDI
import williankl.accountkt.ui.application.DI.applicationDI
import williankl.accountkt.ui.application.currency.ConverterStateHandler.Companion.LocalConverterStateHandler
import williankl.accountkt.ui.application.currency.ConverterStateHandler.Companion.rememberConverterStateHandler
import williankl.accountkt.ui.application.currency.CurrencyDisplayScreen
import williankl.accountkt.ui.application.safeArea.LocalSafeAreaPadding
import williankl.accountkt.ui.application.safeArea.safeAreaPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CommonApplicationContent() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalConverterStateHandler provides rememberConverterStateHandler(),
            LocalSafeAreaPadding provides safeAreaPadding,
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