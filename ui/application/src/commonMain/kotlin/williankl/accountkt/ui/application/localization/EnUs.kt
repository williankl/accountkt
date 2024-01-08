package williankl.accountkt.ui.application.localization

import cafe.adriel.lyricist.LyricistStrings
import williankl.accountkt.ui.application.localization.ApplicationStrings.CurrencyDisplayStrings

@LyricistStrings(languageTag = "en-US", default = true)
internal val enUsStrings = ApplicationStrings(
    currencyStrings = CurrencyDisplayStrings(
        appName = "Kash",
        currenciesLabel = "Currencies",
        favouritesLabel = "Favourites",
        settingsIconDescription = "Settings button",
        magnifyingGlassesIconDescription = "Search icon",
        favouriteToggleIconDescription = { isFavourite ->
            if (isFavourite) "Toggle symbol to not favourite"
            else "Toggle symbol to favourite"
        },
        symbolFlagIconDescription = { symbol -> "$symbol flag" },
        toggleSearchIconDescription = { isSearching ->
            if (isSearching) "Close symbol search"
            else "Open symbol search"
        },
    )
)