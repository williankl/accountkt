package williankl.accountkt.ui.design.core.icons

import androidx.compose.ui.graphics.painter.Painter as ComposePainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public sealed class IconData(
    public val description: String?,
    public val onClick: (() -> Unit)?,
) {
    public class Vector(
        public val imageVector: ImageVector,
        description: String? = null,
        onClick: (() -> Unit)? = null
    ): IconData(description, onClick)

    public class Painter(
        public val painter: ComposePainter,
        description: String? = null,
        onClick: (() -> Unit)? = null
    ): IconData(description, onClick)
}