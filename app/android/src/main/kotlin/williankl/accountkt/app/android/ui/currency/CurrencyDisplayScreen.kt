package williankl.accountkt.app.android.ui.currency

import android.graphics.Paint.Align
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import williankl.accountkt.data.currencyService.api.CurrencyEndpointConstants
import williankl.accountkt.data.currencyService.api.CurrencyEndpointConstants.currencyImageUrl
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.data.currencyService.models.SymbolName

internal class CurrencyDisplayScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<CurrencyDisplayViewModel>()
        val presentation by viewModel.presentation.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.retrieveAllInfoForSymbol("BRL")
        }

        CurrencyDisplayContent(
            presentation = presentation,
            onFavouriteToggle = viewModel::toggleFavourite,
            modifier = Modifier.fillMaxSize()
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CurrencyDisplayContent(
        presentation: CurrencyDisplayViewModel.CurrencyDisplayPresentation,
        onFavouriteToggle: (Symbol, Boolean) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        var ratioStringForBaseSymbol by remember {
            mutableStateOf("1.0")
        }

        var ratioForBaseSymbol by remember {
            mutableFloatStateOf(1f)
        }

        val rates = remember(presentation) {
            presentation.currencyData?.rates.orEmpty()
                .sortedBy { rate -> !rate.isFavourite }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier,
        ) {
            stickyHeader {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = ratioStringForBaseSymbol,
                    onValueChange = { newValue ->
                        newValue.toFloatOrNull()
                            ?.let { ratioForBaseSymbol = it }

                        ratioStringForBaseSymbol = newValue
                    }
                )
            }

            items(
                key = { rate -> rate.symbol },
                items = rates,
            ) { rate ->
                val animatedValue by animateFloatAsState(
                    label = "${rate.symbol}-value-animation",
                    targetValue = (ratioForBaseSymbol * rate.rate).toFloat()
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

                Text(
                    text = "$name ($symbol)"
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                if (isFavourite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                text = "%.2f".format(parsedValue)
            )
        }
    }
}