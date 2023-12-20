package williankl.accountkt.ui.application.currency

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
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
import williankl.accountkt.ui.design.core.bottomElevation
import williankl.accountkt.ui.design.core.button.Button
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.icons.Icon
import williankl.accountkt.ui.design.core.icons.IconData
import williankl.accountkt.ui.design.core.input.CoreTextInput
import williankl.accountkt.ui.design.core.input.TextInput
import williankl.accountkt.ui.design.core.text.CoreText

internal class CurrencyDisplayScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<CurrencyDisplayViewModel>()
        val presentation by viewModel.presentation.collectAsState()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val focusManager = LocalFocusManager.current

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
                focusManager.clearFocus(force = true)
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
        var searchQuery by remember {
            mutableStateOf("")
        }

        var isSearching by remember {
            mutableStateOf(false)
        }

        val (favouriteRates, nonFavouriteRates) = remember(presentation, searchQuery, isSearching) {
            presentation.currencyData?.rates.orEmpty()
                .filter { rate ->
                    rate.symbol.contains(searchQuery, ignoreCase = true) ||
                            rate.name.contains(searchQuery, ignoreCase = true) ||
                            isSearching.not()
                }
                .partition { rate -> rate.isFavourite }
        }

        Column(
            modifier = modifier.background(KtColor.Background.animatedColor)
        ) {
            SearchBar(
                toggleIsSearching = { isSearching = it },
                onQueryChanged = { searchQuery = it },
                query = searchQuery,
                isSearching = isSearching,
                modifier = Modifier,
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                stickyHeader {
                    SymbolConfigItem(
                        onSymbolChangeRequested = onSymbolChangeRequested,
                        stateHandler = stateHandler,
                        modifier = Modifier
                            .shadow(12.dp)
                            .background(KtColor.Background.animatedColor)
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }

                currencyItems(
                    label = "Favourites",
                    rates = favouriteRates,
                    stateHandler = stateHandler,
                    onFavouriteToggle = onFavouriteToggle,
                )

                currencyItems(
                    label = "Currencies",
                    rates = nonFavouriteRates,
                    stateHandler = stateHandler,
                    onFavouriteToggle = onFavouriteToggle,
                )
            }
        }
    }

    @Composable
    private fun SearchBar(
        toggleIsSearching: (Boolean) -> Unit,
        onQueryChanged: (String) -> Unit,
        query: String,
        isSearching: Boolean,
        modifier: Modifier = Modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(
                    color = KtColor.Surface.animatedColor,
                    shape = RoundedCornerShape(
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp,
                    )
                )
                .height(60.dp)
                .padding(12.dp)
                .fillMaxWidth(),
        ) {
            AnimatedVisibility(
                visible = isSearching,
                content = {
                    TextInput(
                        value = query,
                        onValueChange = onQueryChanged,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = IconData.Vector(
                            imageVector = Icons.Outlined.Clear,
                            onClick = { toggleIsSearching(false) }
                        )
                    )
                }
            )

            AnimatedVisibility(
                visible = isSearching.not(),
                content = {
                    Icon(
                        iconData = IconData.Vector(
                            imageVector = Icons.Outlined.Search,
                            onClick = { toggleIsSearching(true) }
                        )
                    )
                }
            )
        }
    }

    @Composable
    private fun SymbolConfigItem(
        onSymbolChangeRequested: () -> Unit,
        stateHandler: ConverterStateHandler,
        modifier: Modifier = Modifier,
    ) {
        val focusManager = LocalFocusManager.current
        var ratioStringForBaseSymbol by remember {
            mutableStateOf(stateHandler.ratio.toString())
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            TextInput(
                modifier = Modifier.weight(2f),
                value = ratioStringForBaseSymbol,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(force = true)
                    }
                ),
                onValueChange = { newValue ->
                    newValue.toFloatOrNull()
                        ?.let { stateHandler.ratio = it }

                    ratioStringForBaseSymbol = newValue
                }
            )

            Button(
                label = stateHandler.symbol,
                onClick = { onSymbolChangeRequested() },
                modifier = Modifier.weight(1f),
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun LazyListScope.currencyItems(
        label: String,
        rates: List<CurrencyRate>,
        stateHandler: ConverterStateHandler,
        onFavouriteToggle: (Symbol, Boolean) -> Unit,
    ) {
        if (rates.isNotEmpty()) {
            item(
                key = label,
            ) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(12.dp)
                        .animateItemPlacement()
                )
            }
        }

        itemsIndexed(
            key = { _, rate -> rate.symbol },
            items = rates,
        ) { index, rate ->
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
                onFavouriteToggleRequested = { onFavouriteToggle(rate.symbol, !rate.isFavourite) },
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(
                        vertical = 4.dp,
                        horizontal = 6.dp,
                    )
                    .fillMaxWidth(),
            )

            if (index == rates.lastIndex) {
                Spacer(
                    modifier = Modifier
                        .bottomElevation(10.dp)
                        .background(KtColor.Background.animatedColor)
                        .fillMaxWidth()
                        .height(6.dp)
                )
            }
        }
    }

    @Composable
    private fun CurrencyRateItem(
        iconUrl: String,
        symbol: Symbol,
        name: SymbolName,
        parsedValue: Float,
        isFavourite: Boolean,
        onFavouriteToggleRequested: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(
                    color = KtColor.Surface.animatedColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(6.dp),
        ) {
            val resource = asyncPainterResource(iconUrl)
            KamelImage(
                resource = resource,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )

            CoreText(
                text = "$name ($symbol)",
                modifier = Modifier.weight(2f),
            )

            CoreText(
                text = "%.2f".format(parsedValue),
                weight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )

            AnimatedContent(
                label = "$symbol-favourite-animation",
                targetState = isFavourite,
                content = { favourite ->
                    val icon =
                        if (favourite) Icons.Filled.Favorite
                        else Icons.Outlined.FavoriteBorder

                    Icon(
                        iconData = IconData.Vector(icon),
                        modifier = Modifier
                            .clickable { onFavouriteToggleRequested() }
                            .size(24.dp),
                    )
                }
            )
        }
    }
}