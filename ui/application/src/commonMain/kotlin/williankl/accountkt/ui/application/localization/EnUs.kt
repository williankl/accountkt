package williankl.accountkt.ui.application.localization

import cafe.adriel.lyricist.LyricistStrings
import williankl.accountkt.ui.application.localization.ApplicationStrings.CurrencyDisplayStrings
import williankl.accountkt.ui.application.localization.ApplicationStrings.SettingsStrings
import williankl.accountkt.ui.application.localization.ApplicationStrings.SymbolSelectionStrings

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
    ),
    settingsStrings = SettingsStrings(
        githubLabel = "Application Github",
        githubIconDescription = "Github icon",
        themeToggleLabel = "Toggle application theme",
        themeToggleIconDescription = "Toggle theme icon",
        versionLabel = { version -> "App Version $version" },
    ),
    symbolSelectionStrings = SymbolSelectionStrings(
        magnifyingGlassesIconDescription = "Search icon",
        queryClearIconDescription = "Clear the search query",
        symbolFlagIconDescription = { symbol -> "$symbol flag" },
    )
)