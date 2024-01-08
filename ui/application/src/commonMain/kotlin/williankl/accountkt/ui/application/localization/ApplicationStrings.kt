package williankl.accountkt.ui.application.localization

import williankl.accountkt.data.currencyService.models.Symbol

internal data class ApplicationStrings(
    val currencyStrings: CurrencyDisplayStrings,
) {
    internal data class CurrencyDisplayStrings(
        val appName: String,
        val currenciesLabel: String,
        val favouritesLabel: String,
        val settingsIconDescription: String,
        val magnifyingGlassesIconDescription: String,
        val favouriteToggleIconDescription: (Boolean) -> String,
        val symbolFlagIconDescription: (Symbol) -> String,
        val toggleSearchIconDescription: (Boolean) -> String,
    )
}