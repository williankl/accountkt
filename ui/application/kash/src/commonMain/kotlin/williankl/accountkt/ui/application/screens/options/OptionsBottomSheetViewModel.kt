package williankl.accountkt.ui.application.screens.options

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.feature.currencyFeature.CurrencyDataRetriever
import williankl.accountkt.feature.currencyFeature.models.CurrencyData
import williankl.accountkt.feature.sharedPreferences.models.CurrencyPreferences
import williankl.accountkt.feature.sharedPreferences.services.CurrencyPreferencesService
import williankl.accountkt.ui.application.platform.PlatformSharedActions

internal class OptionsBottomSheetViewModel(
    private val platformSharedActions: PlatformSharedActions,
) : ScreenModel {

    private companion object {
        const val GITHUB_PAGE_URL = "https://github.com/williankl/accountkt"
    }

    fun redirectToGithubPage() {
        platformSharedActions.openUrl(GITHUB_PAGE_URL)
    }
}