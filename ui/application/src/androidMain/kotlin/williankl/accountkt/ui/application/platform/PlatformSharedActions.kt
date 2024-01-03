package williankl.accountkt.ui.application.platform

import android.content.Context
import android.content.Intent
import android.net.Uri


public actual class PlatformSharedActions(
    private val context: Context,
) {
    public actual fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}