package williankl.accountkt.feature.sharedPreferences

import com.russhwolf.settings.Settings
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.accountkt.feature.sharedPreferences.internal.CurrencyPreferencesServiceImplementation
import williankl.accountkt.feature.sharedPreferences.internal.PreferencesProvider
import williankl.accountkt.feature.sharedPreferences.internal.ThemePreferencesServiceImplementation
import williankl.accountkt.feature.sharedPreferences.models.PreferencesKeys.CURRENCY_PREFERENCES_KEY
import williankl.accountkt.feature.sharedPreferences.models.PreferencesKeys.THEME_PREFERENCES_KEY
import williankl.accountkt.feature.sharedPreferences.services.CurrencyPreferencesService
import williankl.accountkt.feature.sharedPreferences.services.ThemePreferencesService

internal expect fun platformSharedPreferencesDI(): DI.Module

public val sharedPreferencesDI: DI.Module =
    DI.Module("williankl.accountkt.feature.sharedPreferences") {
        import(platformSharedPreferencesDI())

        bindSingleton {
            PreferencesProvider(
                factory = instance(),
            )
        }

        bindSingleton(CURRENCY_PREFERENCES_KEY) {
            instance<PreferencesProvider>()
                .providePreferencesForKey(CURRENCY_PREFERENCES_KEY)
        }

        bindSingleton(THEME_PREFERENCES_KEY) {
            instance<PreferencesProvider>()
                .providePreferencesForKey(THEME_PREFERENCES_KEY)
        }

        bindSingleton<CurrencyPreferencesService> {
            CurrencyPreferencesServiceImplementation(
                settings = instance(CURRENCY_PREFERENCES_KEY)
            )
        }

        bindSingleton<ThemePreferencesService> {
            ThemePreferencesServiceImplementation(
                settings = instance(THEME_PREFERENCES_KEY)
            )
        }
    }