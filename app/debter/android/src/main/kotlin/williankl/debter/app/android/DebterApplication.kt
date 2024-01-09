package williankl.debter.app.android

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import williankl.debter.ui.application.DI.applicationDI

internal class DebterApplication : Application(), DIAware {
    override val di: DI = DI {
        import(androidCoreModule(this@DebterApplication))
        import(androidXModule(this@DebterApplication))
        import(applicationDI)
    }
}