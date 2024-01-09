package williankl.accountkt.feature.sharedPreferences.internal

import com.russhwolf.settings.Settings

internal class PreferencesProvider(
    private val factory: Settings.Factory,
) {
    fun providePreferencesForKey(key: String): Settings {
        return factory.create(key)
    }
}