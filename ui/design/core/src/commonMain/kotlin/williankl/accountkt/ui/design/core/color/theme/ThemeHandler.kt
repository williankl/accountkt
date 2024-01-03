package williankl.accountkt.ui.design.core.color.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

public class ThemeHandler(
    initialTheme: KtTheme,
) {
    public var currentTheme: KtTheme by mutableStateOf(initialTheme)
        private set

    public fun setTheme(theme: KtTheme) {
        currentTheme = theme
    }
}