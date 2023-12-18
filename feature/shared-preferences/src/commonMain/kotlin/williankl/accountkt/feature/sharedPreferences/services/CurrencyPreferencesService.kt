package williankl.accountkt.feature.sharedPreferences.services

import williankl.accountkt.feature.sharedPreferences.models.CurrencyPreferences

public interface CurrencyPreferencesService {
    public val currencyPreferences: CurrencyPreferences?
    public fun updateCurrencyPreferences(
        action: CurrencyPreferences?.() -> CurrencyPreferences,
    )
}