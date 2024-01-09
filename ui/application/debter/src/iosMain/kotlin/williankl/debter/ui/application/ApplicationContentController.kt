package williankl.debter.ui.application

import androidx.compose.ui.window.ComposeUIViewController
import org.kodein.di.compose.withDI
import platform.UIKit.UIViewController
import williankl.accountkt.ui.application.DI.applicationDI

public fun ApplicationContentController(): UIViewController =
    ComposeUIViewController {
        withDI(applicationDI) {
            CommonApplicationContent()
        }
    }