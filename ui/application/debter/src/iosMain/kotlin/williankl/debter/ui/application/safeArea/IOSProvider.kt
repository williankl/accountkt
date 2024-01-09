package williankl.debter.ui.application.safeArea

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

internal actual val safeAreaPadding: SafeAreaPadding =
    SafeAreaPadding(
        paddingValues = PaddingValues(
            top = 60.dp,
            bottom = 20.dp,
        ),
    )