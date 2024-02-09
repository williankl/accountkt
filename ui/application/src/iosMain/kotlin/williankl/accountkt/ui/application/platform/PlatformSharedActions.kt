package williankl.accountkt.ui.application.platform

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

public actual class PlatformSharedActions {
    public actual fun openUrl(url: String) {
        NSURL.URLWithString(url)
            ?.let { parsedNSULR ->
                UIApplication.sharedApplication.openURL(parsedNSULR)
            }
    }

    public actual fun Float.format(decimal: Int): String {
        TODO("Not yet implemented")
    }
}