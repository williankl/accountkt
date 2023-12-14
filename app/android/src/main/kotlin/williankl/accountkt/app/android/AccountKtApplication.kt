package williankl.accountkt.app.android

import android.app.Application
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindSingleton
import williankl.accountkt.app.android.ui.applicationUiDI
import williankl.accountkt.data.currencyService.currencyServiceDI
import williankl.accountkt.data.currencyStorage.currencyRateStorageDI
import williankl.accountkt.feature.currencyFeature.currencyFeatureStorageDI

internal class AccountKtApplication : Application(), DIAware {
    override val di: DI = DI {
        import(currencyServiceDI)
        import(currencyRateStorageDI)
        import(currencyFeatureStorageDI)
        import(applicationUiDI)
        import(androidCoreModule(this@AccountKtApplication))
        import(androidXModule(this@AccountKtApplication))

        bindSingleton {
            Json {
                ignoreUnknownKeys = true
            }
        }
    }
}