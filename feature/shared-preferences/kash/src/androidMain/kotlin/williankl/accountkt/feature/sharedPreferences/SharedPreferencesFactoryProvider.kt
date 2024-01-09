package williankl.accountkt.feature.sharedPreferences

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

internal actual class SharedPreferencesFactoryProvider(
    private val context: Context,
) {
    actual fun provide(): Settings.Factory {
        return SharedPreferencesSettings.Factory(context)
    }
}