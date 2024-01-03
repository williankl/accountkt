package williankl.accountkt.ui.application.DI

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import williankl.accountkt.ui.application.platform.PlatformSharedActions

internal actual fun platformDI() =
    DI.Module("williankl.accountkt.ui.application.platform") {
        bindSingleton {
            PlatformSharedActions()
        }
    }