package williankl.accountkt.ui.application.screens.symbolSelection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Close
import compose.icons.evaicons.outline.Search
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import williankl.accountkt.data.currencyService.api.CurrencyEndpointConstants.currencyImageUrl
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.feature.currencyFeature.models.CurrencyRate
import williankl.accountkt.ui.application.LocalApplicationStrings
import williankl.accountkt.ui.application.safeArea.LocalSafeAreaPadding
import williankl.accountkt.ui.application.screens.currency.ConverterStateHandler.Companion.LocalConverterStateHandler
import williankl.accountkt.ui.design.core.bottomElevation
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.icons.IconData
import williankl.accountkt.ui.design.core.input.TextInput
import williankl.accountkt.ui.design.core.text.CoreText

internal object SymbolSelectionBottomSheet : Screen {
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val stateHandler = LocalConverterStateHandler.currentOrThrow

        SymbolSelectionContent(
            onSymbolSelected = { symbol ->
                stateHandler.symbol = symbol
                bottomSheetNavigator.hide()
            },
            supportedRates = stateHandler.supportedTargetSymbols,
            modifier = Modifier,
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun SymbolSelectionContent(
        onSymbolSelected: (Symbol) -> Unit,
        supportedRates: List<CurrencyRate>,
        modifier: Modifier = Modifier,
    ) {
        val symbolSelectionStrings = LocalApplicationStrings.current.symbolSelectionStrings
        val safeAreaPadding = LocalSafeAreaPadding.current
        val focusManager = LocalFocusManager.current
        val inputFieldFocusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            inputFieldFocusRequester.requestFocus()
        }

        var queryStr by remember {
            mutableStateOf("")
        }

        val (favouriteItems, nonFavouriteItems) = remember(supportedRates, queryStr) {
            supportedRates
                .filter { rate ->
                    rate.symbol.contains(queryStr, ignoreCase = true) ||
                            rate.name.contains(queryStr, ignoreCase = true) ||
                            queryStr.isBlank()
                }
                .partition { rate -> rate.isFavourite }
        }

        LazyColumn(
            modifier = modifier,
        ) {
            stickyHeader {
                TextInput(
                    value = queryStr,
                    onValueChange = { queryStr = it },
                    headingIcon = IconData.Vector(
                        imageVector = EvaIcons.Outline.Search,
                        description = symbolSelectionStrings.magnifyingGlassesIconDescription,
                    ),
                    trailingIcon = IconData.Vector(
                        onClick = { queryStr = "" },
                        imageVector = EvaIcons.Outline.Close,
                        description = symbolSelectionStrings.queryClearIconDescription,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus(force = true)
                        }
                    ),
                    modifier = Modifier
                        .focusRequester(inputFieldFocusRequester)
                        .bottomElevation(10.dp)
                        .background(KtColor.Background.animatedColor)
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        )
                        .fillMaxWidth(),
                )
            }

            currencyItems(
                rates = favouriteItems,
                onSymbolSelected = onSymbolSelected,
            )

            currencyItems(
                rates = nonFavouriteItems,
                onSymbolSelected = onSymbolSelected,
            )

            item {
                Spacer(
                    modifier = Modifier.height(safeAreaPadding.bottomPadding)
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun LazyListScope.currencyItems(
        rates: List<CurrencyRate>,
        onSymbolSelected: (Symbol) -> Unit,
    ) {
        items(
            key = { rate -> rate.symbol },
            items = rates,
        ) { rate ->
            SymbolItem(
                rate = rate,
                modifier = Modifier
                    .animateItemPlacement()
                    .clickable { onSymbolSelected(rate.symbol) }
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    private fun SymbolItem(
        rate: CurrencyRate,
        modifier: Modifier = Modifier,
    ) {
        val symbolSelectionStrings = LocalApplicationStrings.current.symbolSelectionStrings

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(
                    vertical = 18.dp,
                    horizontal = 12.dp,
                )
        ) {
            val resource = asyncPainterResource(currencyImageUrl(rate.symbol))
            KamelImage(
                resource = resource,
                contentDescription = symbolSelectionStrings.symbolFlagIconDescription(rate.symbol),
                modifier = Modifier.size(24.dp)
            )

            CoreText(
                text = "${rate.name} (${rate.symbol})"
            )
        }
    }
}