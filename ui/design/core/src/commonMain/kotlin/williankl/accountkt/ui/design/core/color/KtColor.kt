package williankl.accountkt.ui.design.core.color

import androidx.compose.ui.graphics.Color

public sealed class KtColor(
    public val composeLightColor: Color,
    public val composeDarkColor: Color,
) {

    public data object PrimaryLow : KtColor(
        composeDarkColor = Color(0xFFFBD968),
        composeLightColor = Color(0xFFFBD968),
    )

    public data object Primary : KtColor(
        composeDarkColor = Color(0xFFE2C35E),
        composeLightColor = Color(0xFFE2C35E),
    )

    public data object PrimaryHigh : KtColor(
        composeDarkColor = Color(0xFFC9AE53),
        composeLightColor = Color(0xFFC9AE53),
    )

    public data object SecondaryLow : KtColor(
        composeDarkColor = Color(0xFFF78E4C),
        composeLightColor = Color(0xFF4273BC),
    )

    public data object Secondary : KtColor(
        composeDarkColor = Color(0xFFEB8F48),
        composeLightColor = Color(0xFF355C96),
    )

    public data object SecondaryHigh : KtColor(
        composeDarkColor = Color(0xFF2D2D30),
        composeLightColor = Color(0xFF2E5184),
    )

    public data object NeutralLow : KtColor(
        composeDarkColor = Color(0xFFD9E3F2),
        composeLightColor = Color(0xFFD9E3F2),
    )

    public data object Neutral : KtColor(
        composeDarkColor = Color(0xFF142238),
        composeLightColor = Color(0xFF142238),
    )

    public data object NeutralHigh : KtColor(
        composeDarkColor = Color(0xFF120F0F),
        composeLightColor = Color(0xFF120F0F),
    )

    public data object Surface : KtColor(
        composeDarkColor = Color(0xFF3E3E42),
        composeLightColor = Color(0xFFDBDBDB),
    )

    public data object Border : KtColor(
        composeDarkColor = Color(0xFFF1F1F1),
        composeLightColor = Color(0xFFF1F1F1),
    )

    public data object Background : KtColor(
        composeDarkColor = Color(0xFF1E1E1E),
        composeLightColor = Color(0xFFFAFAFA),
    )

    public data object Transparent : KtColor(
        composeDarkColor = Color.Transparent,
        composeLightColor = Color.Transparent,
    )
}