package williankl.accountkt.ui.design.core.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.compose.asFont
import dev.icerock.moko.resources.compose.fontFamilyResource
import williankl.accountkt.ui.design.core.SharedResources

@Composable
internal fun retrieveFontFamily() = FontFamily(
    fonts = listOfNotNull(
        SharedResources.fonts.Montserrat.bold.asFont(
            weight = FontWeight.Bold,
            style = FontStyle.Normal,
        ),
        SharedResources.fonts.Montserrat.boldItalic.asFont(
            weight = FontWeight.Bold,
            style = FontStyle.Italic,
        ),

        SharedResources.fonts.Montserrat.semiBold.asFont(
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal,
        ),
        SharedResources.fonts.Montserrat.semiBoldItalic.asFont(
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic,
        ),
        SharedResources.fonts.Montserrat.regular.asFont(
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        ),
        SharedResources.fonts.Montserrat.italic.asFont(
            weight = FontWeight.Normal,
            style = FontStyle.Italic,
        ),
    )
)