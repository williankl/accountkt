package williankl.accountkt.app.android.ui.currency

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import williankl.accountkt.data.currencyService.api.CurrencyEndpointConstants.currencyImageUrl
import williankl.accountkt.data.currencyService.models.Symbol
import williankl.accountkt.feature.currencyFeature.models.CurrencyRate
import williankl.accountkt.ui.design.core.text.CoreText

internal class SymbolSelectionBottomSheet(
    private val onSymbolSelected: (Symbol) -> Unit,
    private val supportedRates: List<CurrencyRate>,
) : Screen {
    @Composable
    override fun Content() {
        SymbolSelectionContent(
            onSymbolSelected = onSymbolSelected,
            supportedRates = supportedRates,
            modifier = Modifier,
        )
    }

    @Composable
    private fun SymbolSelectionContent(
        onSymbolSelected: (Symbol) -> Unit,
        supportedRates: List<CurrencyRate>,
        modifier: Modifier = Modifier,
    ) {
        val (favouriteItems, nonFavouriteItems) = remember(supportedRates) {
            supportedRates.partition { rate -> rate.isFavourite }
        }

        LazyColumn(
            modifier = modifier,
        ) {
            currencyItems(
                rates = favouriteItems,
                onSymbolSelected = onSymbolSelected,
            )

            currencyItems(
                rates = nonFavouriteItems,
                onSymbolSelected = onSymbolSelected,
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun LazyListScope.currencyItems(
        rates: List<CurrencyRate>,
        onSymbolSelected: (Symbol) -> Unit,
    ) {
        items(rates) { rate ->
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
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            CoreText(
                text = "${rate.name} (${rate.symbol})"
            )
        }
    }
}