package williankl.accountkt.app.android.ui.currency

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import williankl.accountkt.data.currencyService.api.CurrencyEndpointConstants.currencyImageUrl
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.feature.currencyFeature.models.CurrencyRate
import williankl.accountkt.ui.design.core.icons.CoreIcon
import williankl.accountkt.ui.design.core.input.CoreTextInput
import williankl.accountkt.ui.design.core.text.CoreText

internal class CurrencyDisplayScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<CurrencyDisplayViewModel>()
        val presentation by viewModel.presentation.collectAsState()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        val stateHandler = remember {
            with(viewModel.currencyPreferencesOrDefault()) {
                ConverterStateHandler(
                    initSymbol = preferredSymbol,
                    initRatio = defaultAmount,
                )
            }
        }

        LaunchedEffect(stateHandler.symbol) {
            viewModel.retrieveAllInfoForSymbol(stateHandler.symbol)
        }

        LaunchedEffect(stateHandler.symbol, stateHandler.ratio) {
            viewModel.saveState(stateHandler)
        }

        CurrencyDisplayContent(
            presentation = presentation,
            onFavouriteToggle = viewModel::toggleFavourite,
            onSymbolChangeRequested = {
                bottomSheetNavigator.show(
                    SymbolSelectionBottomSheet(
                        onSymbolSelected = { selected ->
                            bottomSheetNavigator.hide()
                            stateHandler.symbol = selected
                        },
                        supportedRates = presentation.currencyData?.rates.orEmpty(),
                    )
                )
            },
            stateHandler = stateHandler,
            modifier = Modifier.fillMaxSize()
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CurrencyDisplayContent(
        presentation: CurrencyDisplayViewModel.CurrencyDisplayPresentation,
        onFavouriteToggle: (Symbol, Boolean) -> Unit,
        onSymbolChangeRequested: () -> Unit,
        stateHandler: ConverterStateHandler,
        modifier: Modifier = Modifier,
    ) {
        val (favouriteRates, nonFavouriteRates) = remember(presentation) {
            presentation.currencyData?.rates.orEmpty()
                .partition { rate -> rate.isFavourite }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier,
        ) {
            stickyHeader {
                SymbolConfigItem(
                    onSymbolChangeRequested = onSymbolChangeRequested,
                    stateHandler = stateHandler,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            currencyItems(
                rates = favouriteRates,
                stateHandler = stateHandler,
                onFavouriteToggle = onFavouriteToggle,
            )

            currencyItems(
                rates = nonFavouriteRates,
                stateHandler = stateHandler,
                onFavouriteToggle = onFavouriteToggle,
            )
        }
    }

    @Composable
    private fun SymbolConfigItem(
        onSymbolChangeRequested: () -> Unit,
        stateHandler: ConverterStateHandler,
        modifier: Modifier = Modifier,
    ) {
        var ratioStringForBaseSymbol by remember {
            mutableStateOf(stateHandler.ratio.toString())
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            CoreTextInput(
                modifier = Modifier.weight(1f),
                value = ratioStringForBaseSymbol,
                onValueChange = { newValue ->
                    newValue.toFloatOrNull()
                        ?.let { stateHandler.ratio = it }

                    ratioStringForBaseSymbol = newValue
                }
            )

            CoreText(
                text = stateHandler.symbol,
                modifier = Modifier
                    .clickable { onSymbolChangeRequested() }
                    .weight(1f)
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun LazyListScope.currencyItems(
        rates: List<CurrencyRate>,
        stateHandler: ConverterStateHandler,
        onFavouriteToggle: (Symbol, Boolean) -> Unit,
    ) {
        items(
            key = { rate -> rate.symbol },
            items = rates,
        ) { rate ->
            val animatedValue by animateFloatAsState(
                label = "${rate.symbol}-value-animation",
                targetValue = (stateHandler.ratio * rate.rate).toFloat()
            )

            CurrencyRateItem(
                iconUrl = currencyImageUrl(rate.symbol),
                name = rate.name,
                symbol = rate.symbol,
                parsedValue = animatedValue,
                isFavourite = rate.isFavourite,
                modifier = Modifier
                    .animateItemPlacement()
                    .clickable { onFavouriteToggle(rate.symbol, !rate.isFavourite) }
                    .fillMaxWidth(),
            )
        }
    }

    @Composable
    private fun CurrencyRateItem(
        iconUrl: String,
        symbol: Symbol,
        name: SymbolName,
        parsedValue: Float,
        isFavourite: Boolean,
        modifier: Modifier = Modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp,
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val resource = asyncPainterResource(iconUrl)
                KamelImage(
                    resource = resource,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                CoreText(
                    text = "$name ($symbol)"
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                if (isFavourite) {
                    CoreIcon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            CoreText(
                text = "%.2f".format(parsedValue)
            )
        }
    }
}