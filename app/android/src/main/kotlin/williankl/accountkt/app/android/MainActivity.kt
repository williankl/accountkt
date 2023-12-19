package williankl.accountkt.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import williankl.accountkt.app.android.ui.currency.CurrencyDisplayScreen

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun AppContent() {
        BottomSheetNavigator(
            sheetBackgroundColor = Color.Black.copy(alpha = 0.25f),
            sheetContentColor = Color.White,
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