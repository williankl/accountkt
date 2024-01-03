package williankl.accountkt.ui.application.platform

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

public actual class PlatformSharedActions {
    public actual fun openUrl(url: String) {
        UIApplication.sharedApplication.openURL(
            url = NSURL("https://github.com/williankl/accountkt")
        )
    }
}