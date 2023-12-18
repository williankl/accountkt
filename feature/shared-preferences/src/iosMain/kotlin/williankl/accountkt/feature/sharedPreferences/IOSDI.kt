package williankl.accountkt.feature.sharedPreferences

import org.kodein.di.DI
import org.kodein.di.bindSingleton

internal actual fun platformSharedPreferencesDI(): DI.Module =
    DI.Module("williankl.accountkt.feature.sharedPreferences.ios") {
        bindSingleton {
            SharedPreferencesFactoryProvider()
                .provide()
        }
    }