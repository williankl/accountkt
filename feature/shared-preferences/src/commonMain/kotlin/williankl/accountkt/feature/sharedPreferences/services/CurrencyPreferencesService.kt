package williankl.accountkt.feature.sharedPreferences.services

import williankl.accountkt.feature.sharedPreferences.models.CurrencyPreferences
import williankl.accountkt.ui.design.core.color.theme.KtTheme

public interface CurrencyPreferencesService {
    public val currencyPreferences: CurrencyPreferences?
    public fun updateCurrencyPreferences(
        action: CurrencyPreferences?.() -> CurrencyPreferences,
    )
}