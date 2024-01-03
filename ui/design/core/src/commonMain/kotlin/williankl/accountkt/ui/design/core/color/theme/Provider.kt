package williankl.accountkt.ui.design.core.color.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalKtTheme: ProvidableCompositionLocal<ThemeHandler> =
    staticCompositionLocalOf { ThemeHandler(KtTheme.System) }