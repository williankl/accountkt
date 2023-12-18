package williankl.accountkt.feature.sharedPreferences.internal

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import williankl.accountkt.feature.sharedPreferences.models.CurrencyPreferences
import williankl.accountkt.feature.sharedPreferences.services.CurrencyPreferencesService

internal class CurrencyPreferencesServiceImplementation(
    private val settings: Settings,
) : CurrencyPreferencesService {

    private companion object {
        const val SYMBOL_KEY = "symbol"
        const val SYMBOL_VALUE_KEY = "symbol-value"
    }

    override val currencyPreferences: CurrencyPreferences?
        get() = retrieveCurrencyPreferencesOrNull()

    override fun updateCurrencyPreferences(
        action: CurrencyPreferences?.() -> CurrencyPreferences
    ) {
        with(retrieveCurrencyPreferencesOrNull()) {
            updateCurrencyPreference(action())
        }
    }

    private fun updateCurrencyPreference(to: CurrencyPreferences) {
        settings[SYMBOL_KEY] = to.preferredSymbol
        settings[SYMBOL_VALUE_KEY] = to.defaultAmount
    }

    private fun retrieveCurrencyPreferencesOrNull(): CurrencyPreferences? {
        val symbol = settings.getStringOrNull(SYMBOL_KEY)
        val symbolValue = settings.getDoubleOrNull(SYMBOL_VALUE_KEY)

        return if (symbol != null && symbolValue != null) {
            CurrencyPreferences(
                preferredSymbol = symbol,
                defaultAmount = symbolValue,
            )
        } else null
    }

}