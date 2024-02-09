package williankl.accountkt.ui.application.platform

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri


public actual class PlatformSharedActions(
    private val context: Context,
) {
    public actual fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags += FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    public actual fun Float.format(decimal: Int): String {
        return String.format("%.2f", decimal)
    }
}