package williankl.accountkt.ui.design.core.color

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import williankl.accountkt.ui.design.core.color.theme.KtTheme
import williankl.accountkt.ui.design.core.color.theme.LocalKtTheme

public fun KtColor.composeColor(
    isInDarkMode: Boolean,
    theme: KtTheme = KtTheme.System,
): Color {
    return when (theme) {
        KtTheme.Light -> composeLightColor
        KtTheme.Dark -> composeDarkColor
        KtTheme.System -> if (isInDarkMode) composeDarkColor else composeLightColor
    }
}

@Composable
public fun KtColor.composeColor(
    theme: KtTheme = KtTheme.System,
): Color {
    return composeColor(isSystemInDarkTheme(), theme)
}

public val KtColor.composeColor: Color
    @Composable get() = composeColor(LocalKtTheme.current.currentTheme)

@Composable
public fun KtColor.animatedColor(
    theme: KtTheme = KtTheme.System,
): State<Color> {
    return animateColorAsState(
        targetValue = composeColor(isSystemInDarkTheme(), theme)
    )
}

public val KtColor.animatedColorAsState: State<Color>
    @Composable get() = animateColorAsState(
        targetValue = composeColor(LocalKtTheme.current.currentTheme)
    )

@Composable
public fun KtColor.animatedColor(alpha: Float = 1f): Color {
    return animatedColor.copy(alpha = alpha)
}

public val KtColor.animatedColor: Color
    @Composable get() {
        val animatedColor by animatedColorAsState
        return animatedColor
    }


