package williankl.accountkt.ui.application.platform

public expect class PlatformSharedActions {
    public fun openUrl(url: String)

    public fun Float.format(decimal: Int = 2): String
}