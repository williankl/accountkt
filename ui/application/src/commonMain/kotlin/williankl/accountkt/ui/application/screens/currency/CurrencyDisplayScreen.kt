package williankl.accountkt.ui.application.screens.currency

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Heart
import compose.icons.evaicons.outline.Close
import compose.icons.evaicons.outline.Heart
import compose.icons.evaicons.outline.Search
import compose.icons.evaicons.outline.Settings
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import williankl.accountkt.data.currencyService.api.CurrencyEndpointConstants.currencyImageUrl
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName
import williankl.accountkt.feature.currencyFeature.models.CurrencyRate
import williankl.accountkt.ui.application.screens.currency.ConverterStateHandler.Companion.LocalConverterStateHandler
import williankl.accountkt.ui.application.safeArea.LocalSafeAreaPadding
import williankl.accountkt.ui.application.screens.options.OptionsBottomSheet
import williankl.accountkt.ui.application.screens.symbolSelection.SymbolSelectionBottomSheet
import williankl.accountkt.ui.design.core.SharedResources
import williankl.accountkt.ui.design.core.bottomElevation
import williankl.accountkt.ui.design.core.button.Button
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.icons.Icon
import williankl.accountkt.ui.design.core.icons.IconData
import williankl.accountkt.ui.design.core.input.TextInput
import williankl.accountkt.ui.design.core.text.CoreText

internal class CurrencyDisplayScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<CurrencyDisplayViewModel>()
        val presentation by viewModel.presentation.collectAsState()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val focusManager = LocalFocusManager.current
        val stateHandler = LocalConverterStateHandler.currentOrThrow

        LifecycleEffect(
            onStarted = {
                val preferences = viewModel.currencyPreferencesOrDefault()
                stateHandler.symbol = preferences.preferredSymbol
                stateHandler.ratio = preferences.defaultAmount
            }
        )

        LaunchedEffect(stateHandler.symbol) {
            viewModel.retrieveAllInfoForSymbol(stateHandler.symbol)
        }

        LaunchedEffect(stateHandler.symbol, stateHandler.ratio) {
            viewModel.saveState(stateHandler)
        }

        LaunchedEffect(presentation.currencyData) {
            stateHandler.supportedTargetSymbols = presentation.currencyData?.rates.orEmpty()
        }

        CurrencyDisplayContent(
            presentation = presentation,
            onFavouriteToggle = viewModel::toggleFavourite,
            onOptionsRequested = {
                bottomSheetNavigator.show(OptionsBottomSheet)
            },
            onSymbolChangeRequested = {
                focusManager.clearFocus(force = true)
                bottomSheetNavigator.show(SymbolSelectionBottomSheet)
            },
            stateHandler = stateHandler,
            modifier = Modifier.fillMaxSize()
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CurrencyDisplayContent(
        presentation: CurrencyDisplayViewModel.CurrencyDisplayPresentation,
        onOptionsRequested: () -> Unit,
        onFavouriteToggle: (Symbol, Boolean) -> Unit,
        onSymbolChangeRequested: () -> Unit,
        stateHandler: ConverterStateHandler,
        modifier: Modifier = Modifier,
    ) {
        var searchQuery by remember(stateHandler.symbol) {
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
                onOptionsRequested = onOptionsRequested,
                toggleIsSearching = { searching ->
                    isSearching = searching
                    if (searching) {
                        searchQuery = ""
                    }
                },
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
                    label = "Favourites", // fixme - add localized label
                    rates = favouriteRates,
                    stateHandler = stateHandler,
                    onFavouriteToggle = onFavouriteToggle,
                )

                currencyItems(
                    label = "Currencies", // fixme - add localized label
                    rates = nonFavouriteRates,
                    stateHandler = stateHandler,
                    onFavouriteToggle = onFavouriteToggle,
                    shouldShowShadow = false,
                )

                item {
                    val safeAreaPadding = LocalSafeAreaPadding.current
                    Spacer(
                        modifier = Modifier.height(safeAreaPadding.bottomPadding)
                    )
                }
            }
        }
    }

    @Composable
    private fun SearchBar(
        onOptionsRequested: () -> Unit,
        toggleIsSearching: (Boolean) -> Unit,
        onQueryChanged: (String) -> Unit,
        query: String,
        isSearching: Boolean,
        modifier: Modifier = Modifier,
    ) {
        val safeAreaPadding = LocalSafeAreaPadding.current
        val focusManager = LocalFocusManager.current
        val inputFieldFocusRequester = remember { FocusRequester() }
        val iconSize = remember { 24.dp }

        LaunchedEffect(isSearching) {
            if (isSearching) {
                inputFieldFocusRequester.requestFocus()
            }
        }

        Column(
            modifier = modifier
                .background(
                    color = KtColor.Surface.animatedColor,
                    shape = RoundedCornerShape(
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp,
                    )
                )
                .padding(top = safeAreaPadding.topPadding)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(SharedResources.images.ic_brand_full),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )

                    CoreText(
                        text = "Kash", // fixme - localize if needed
                        size = 16.sp,
                        weight = FontWeight.Bold,
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                AnimatedContent(
                    targetState = isSearching,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    content = { searching ->
                        Icon(
                            modifier = Modifier.size(iconSize),
                            iconData = IconData.Vector(
                                onClick = { toggleIsSearching(isSearching.not()) },
                                description = null, // fixme - add localized descriptions
                                imageVector =
                                if (searching) EvaIcons.Outline.Close
                                else EvaIcons.Outline.Search,
                            )
                        )
                    }
                )

                Icon(
                    modifier = Modifier.size(iconSize),
                    iconData = IconData.Vector(
                        onClick = onOptionsRequested,
                        imageVector = EvaIcons.Outline.Settings,
                        description = null, // fixme - add localized descriptions
                    ),
                )
            }

            AnimatedVisibility(
                visible = isSearching,
                content = {
                    TextInput(
                        value = query,
                        onValueChange = onQueryChanged,
                        headingIcon = IconData.Vector(
                            imageVector = EvaIcons.Outline.Search,
                            description = null, // fixme - add localized descriptions
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = { focusManager.clearFocus(force = true) }
                        ),
                        modifier = Modifier
                            .focusRequester(inputFieldFocusRequester)
                            .padding(top = 12.dp)
                            .fillMaxWidth(),
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
        shouldShowShadow: Boolean = true,
    ) {
        if (rates.isNotEmpty()) {
            item(
                key = label,
            ) {
                CoreText(
                    text = label,
                    weight = FontWeight.Bold,
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

            if (index == rates.lastIndex && shouldShowShadow) {
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
                contentDescription = null, // fixme - add localized descriptions
                modifier = Modifier.size(30.dp)
            )

            CoreText(
                text = "$name ($symbol)",
                modifier = Modifier.weight(2f),
            )

            CoreText(
                text = parsedValue.parseToFloatingPoint(2),
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
                        if (favourite) EvaIcons.Fill.Heart
                        else EvaIcons.Outline.Heart

                    Icon(
                        tint = KtColor.Secondary,
                        iconData = IconData.Vector(
                            imageVector = icon,
                            description = null, // fixme - add localized descriptions
                        ),
                        modifier = Modifier
                            .clickable { onFavouriteToggleRequested() }
                            .size(24.dp),
                    )
                }
            )
        }
    }

    private fun Float.parseToFloatingPoint(
        number: Int,
        coinSeparator: String = ",",
    ): String {
        return with(toString()) {
            val before = substringBefore(".")
            val after = substringAfter(".")
            "$before$coinSeparator${after.take(number)}"
        }
    }
}