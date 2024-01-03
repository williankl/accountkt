package williankl.accountkt.ui.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.kodein.di.compose.withDI
import williankl.accountkt.ui.application.DI.applicationDI
import williankl.accountkt.ui.application.screens.currency.ConverterStateHandler.Companion.LocalConverterStateHandler
import williankl.accountkt.ui.application.screens.currency.ConverterStateHandler.Companion.rememberConverterStateHandler
import williankl.accountkt.ui.application.screens.currency.CurrencyDisplayScreen
import williankl.accountkt.ui.application.safeArea.LocalSafeAreaPadding
import williankl.accountkt.ui.application.safeArea.safeAreaPadding
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CommonApplicationContent() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalConverterStateHandler provides rememberConverterStateHandler(),
            LocalSafeAreaPadding provides safeAreaPadding,
        ) {
            BottomSheetNavigator(
                scrimColor = Color.Black.copy(alpha = 0.25f),
                sheetBackgroundColor = Color.Transparent,
                sheetContentColor = Color.Transparent,
                sheetElevation = 0.dp,
                sheetContent = {
                    val safeAreaPadding = LocalSafeAreaPadding.current
                    Column {
                        Spacer(
                            modifier = Modifier
                                .padding(top = safeAreaPadding.topPadding)
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            content = { CurrentScreen() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                    )
                                )
                                .background(KtColor.Background.animatedColor)
                        )
                    }
                },
                content = {
                    Navigator(CurrencyDisplayScreen()) {
                        SlideTransition(it)
                    }
                }
            )
        }
    }
}