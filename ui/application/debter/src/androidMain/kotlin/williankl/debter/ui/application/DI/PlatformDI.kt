package williankl.debter.ui.application.DI

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.debter.ui.application.platform.PlatformSharedActions

internal actual fun platformDI() =
    DI.Module("williankl.accountkt.ui.application.platform") {
        bindSingleton {
            PlatformSharedActions(
                context = instance(),
            )
        }
    }