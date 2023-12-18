package williankl.accountkt.feature.sharedPreferences.internal

import com.russhwolf.settings.Settings
import williankl.accountkt.feature.sharedPreferences.models.PreferencesKeys

internal class PreferencesProvider(
    private val factory: Settings.Factory,
) {
    fun providePreferencesForKey(key: String): Settings {
        return factory.create(key)
    }
}