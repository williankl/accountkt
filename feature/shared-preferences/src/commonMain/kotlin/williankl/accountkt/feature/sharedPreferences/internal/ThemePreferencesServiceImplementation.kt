package williankl.accountkt.feature.sharedPreferences.internal

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import williankl.accountkt.feature.sharedPreferences.services.ThemePreferencesService
import williankl.accountkt.ui.design.core.color.theme.KtTheme

internal class ThemePreferencesServiceImplementation(
    private val settings: Settings,
) : ThemePreferencesService {

    private companion object {
        const val THEME_KEY = "app-theme"
    }

    override fun preferredTheme(): KtTheme {
        return settings
            .getStringOrNull(THEME_KEY)
            ?.let(KtTheme::valueOf)
            ?: KtTheme.System
    }

    override fun setPreferredTheme(theme: KtTheme) {
        settings[THEME_KEY] = theme.name
    }
}