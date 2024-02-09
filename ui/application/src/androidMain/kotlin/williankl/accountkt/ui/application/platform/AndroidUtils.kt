package williankl.accountkt.ui.application.platform

public actual fun Float.format(decimal: Int): String {
    return String.format("%.2f", decimal)
}