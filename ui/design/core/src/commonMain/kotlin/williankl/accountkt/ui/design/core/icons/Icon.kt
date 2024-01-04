package williankl.accountkt.ui.design.core.icons

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.color.animatedColorAsState

@Composable
public fun Icon(
    iconData: IconData,
    modifier: Modifier = Modifier,
    tint: KtColor = KtColor.NeutralHigh,
) {
    when (iconData) {
        is IconData.Vector -> {
            Icon(
                imageVector = iconData.imageVector,
                contentDescription = iconData.description,
                tint = tint.animatedColor,
                modifier = modifier.composed {
                    clickableModifierOrNothing(iconData)
                },
            )
        }

        is IconData.Painter -> {
            Icon(
                painter = iconData.painter,
                contentDescription = iconData.description,
                tint = tint.animatedColor,
                modifier = modifier.composed {
                    clickableModifierOrNothing(iconData)
                },
            )
        }
    }
}


private fun Modifier.clickableModifierOrNothing(
    iconData: IconData,
): Modifier =
    composed {
        iconData.onClick
            ?.let { action -> clickable { action() } }
            ?: Modifier
    }