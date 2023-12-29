package williankl.accountkt.ui.application

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

public fun ApplicationContentController(): UIViewController =
    ComposeUIViewController { CommonApplicationContent() }