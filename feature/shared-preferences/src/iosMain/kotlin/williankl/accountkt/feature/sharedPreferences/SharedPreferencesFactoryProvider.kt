package williankl.accountkt.feature.sharedPreferences

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings

internal actual class SharedPreferencesFactoryProvider {
    actual fun provide(): Settings.Factory {
        return NSUserDefaultsSettings.Factory()
    }
}