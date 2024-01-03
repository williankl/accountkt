package williankl.accountkt.feature.sharedPreferences.services

import williankl.accountkt.feature.sharedPreferences.models.CurrencyPreferences
import williankl.accountkt.ui.design.core.color.theme.KtTheme

public interface ThemePreferencesService {
    public fun preferredTheme(): KtTheme

    public fun setPreferredTheme(theme: KtTheme)
}