package williankl.accountkt.ui.design.core.icons

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons as MaterialIcons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedComposeColor

@Composable
public fun CoreIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: KtColor = KtColor.NeutralHigh,
) {
    val animatedTint by tint.animatedComposeColor

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = animatedTint,
    )
}

@Composable
public fun CoreIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: KtColor = KtColor.NeutralHigh,
) {
    val animatedTint by tint.animatedComposeColor

    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = animatedTint,
    )
}