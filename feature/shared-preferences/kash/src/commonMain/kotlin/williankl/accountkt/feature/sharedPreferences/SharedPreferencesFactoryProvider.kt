package williankl.accountkt.feature.sharedPreferences

import com.russhwolf.settings.Settings

internal expect class SharedPreferencesFactoryProvider {
    fun provide(): Settings.Factory
}