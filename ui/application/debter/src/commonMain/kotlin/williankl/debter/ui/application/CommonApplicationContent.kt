package williankl.debter.ui.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import williankl.accountkt.feature.sharedPreferences.services.ThemePreferencesService
import williankl.debter.ui.application.safeArea.LocalSafeAreaPadding
import williankl.debter.ui.application.safeArea.safeAreaPadding
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.color.theme.LocalKtTheme
import williankl.accountkt.ui.design.core.color.theme.ThemeHandler
import williankl.debter.ui.application.screens.main.MainScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CommonApplicationContent() {
    val localDI = localDI()
    val themePreferences by localDI.instance<ThemePreferencesService>()
    val themeHandler = remember {
        ThemeHandler(themePreferences.preferredTheme())
    }

    LaunchedEffect(themeHandler.currentTheme) {
        themePreferences.setPreferredTheme(themeHandler.currentTheme)
    }

    MaterialTheme {
        CompositionLocalProvider(
            LocalSafeAreaPadding provides safeAreaPadding,
            LocalKtTheme provides themeHandler
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
                            modifier = Modifier.height(
                                safeAreaPadding.paddingValues.calculateTopPadding()
                            )
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
                    Navigator(MainScreen) {
                        SlideTransition(it)
                    }
                }
            )
        }
    }
}