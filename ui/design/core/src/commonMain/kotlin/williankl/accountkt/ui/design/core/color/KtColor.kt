package williankl.accountkt.ui.design.core.color

import androidx.compose.ui.graphics.Color

public sealed class KtColor(
    public val composeLightColor: Color,
    public val composeDarkColor: Color,
) {

    public data object PrimaryLow : KtColor(
        composeDarkColor = Color(0xFF3D316B),
        composeLightColor = Color(0xFF3D316B),
    )

    public data object Primary : KtColor(
        composeDarkColor = Color(0xFF312046),
        composeLightColor = Color(0xFF312046),
    )

    public data object PrimaryHigh : KtColor(
        composeDarkColor = Color(0xFF291631),
        composeLightColor = Color(0xFF291631),
    )

    public data object SecondaryLow : KtColor(
        composeDarkColor = Color(0xFFF78E4C),
        composeLightColor = Color(0xFFF78E4C),
    )

    public data object Secondary : KtColor(
        composeDarkColor = Color(0xFFEB8F48),
        composeLightColor = Color(0xFFEB8F48),
    )

    public data object SecondaryHigh : KtColor(
        composeDarkColor = Color(0xFFD17F40),
        composeLightColor = Color(0xFFD17F40),
    )

    public data object Transparent : KtColor(
        composeDarkColor = Color.Transparent,
        composeLightColor = Color.Transparent,
    )
}