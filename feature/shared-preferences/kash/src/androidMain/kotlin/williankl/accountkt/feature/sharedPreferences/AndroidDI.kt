package williankl.accountkt.feature.sharedPreferences

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal actual fun platformSharedPreferencesDI(): DI.Module =
    DI.Module("williankl.accountkt.feature.sharedPreferences.android") {
        bindSingleton {
            SharedPreferencesFactoryProvider(
                context = instance()
            ).provide()
        }
    }