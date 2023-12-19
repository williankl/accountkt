package williankl.accountkt.ui.design.core.color

import androidx.compose.ui.graphics.Color

public sealed class KtColor(
    public val composeLightColor: Color,
    public val composeDarkColor: Color,
) {
    public data object NeutralHigh : KtColor(
        composeDarkColor = Color.DarkGray,
        composeLightColor = Color.DarkGray,
    )
}