package williankl.accountkt.ui.design.core.color.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Moon
import compose.icons.evaicons.outline.Sun

public val LocalKtTheme: ProvidableCompositionLocal<ThemeHandler> =
    staticCompositionLocalOf { ThemeHandler(KtTheme.System) }

public fun KtTheme.iconVector(isSystemInDarkMode: Boolean): ImageVector =
    when (this) {
        KtTheme.Light -> EvaIcons.Outline.Sun
        KtTheme.Dark -> EvaIcons.Outline.Moon
        KtTheme.System ->
            if (isSystemInDarkMode) EvaIcons.Outline.Moon
            else EvaIcons.Outline.Sun
    }

public fun KtTheme.toggleMode(isSystemInDarkMode: Boolean): KtTheme =
    when (this) {
        KtTheme.Light -> KtTheme.Dark
        KtTheme.Dark -> KtTheme.Light
        KtTheme.System ->
            if (isSystemInDarkMode) KtTheme.Light
            else KtTheme.Dark
    }