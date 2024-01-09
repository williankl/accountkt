package williankl.accountkt.app.android

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import williankl.accountkt.ui.application.DI.applicationDI

internal class KashKtApplication : Application(), DIAware {
    override val di: DI = DI {
        import(androidCoreModule(this@KashKtApplication))
        import(androidXModule(this@KashKtApplication))
        import(applicationDI)
    }
}