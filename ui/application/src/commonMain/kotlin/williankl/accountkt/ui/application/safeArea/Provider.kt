package williankl.accountkt.ui.application.safeArea

import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalSafeAreaPadding =
    staticCompositionLocalOf { SafeAreaPadding() }

internal expect val safeAreaPadding: SafeAreaPadding