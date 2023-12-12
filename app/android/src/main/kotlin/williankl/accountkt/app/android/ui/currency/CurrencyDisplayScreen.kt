package williankl.accountkt.app.android.ui.currency

import android.graphics.Paint.Align
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
            modifier = Modifier.fillMaxSize()
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CurrencyDisplayContent(
        presentation: CurrencyDisplayViewModel.CurrencyDisplayPresentation,
        modifier: Modifier = Modifier,
    ) {
        var ratioForBaseSymbol by remember {
            mutableFloatStateOf(1f)
        }

        val rates = remember(presentation.symbolRate) {
            presentation.symbolRate?.rates.orEmpty()
                .map { (symbol, value) -> symbol to value }
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
                    value = ratioForBaseSymbol.toString(),
                    onValueChange = { newValue ->
                        newValue.toFloatOrNull()
                            ?.let { ratioForBaseSymbol = it }
                    }
                )
            }

            items(rates) { (symbol, value) ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 12.dp,
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val resource = asyncPainterResource(currencyImageUrl(symbol))
                        KamelImage(
                            resource = resource,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = (
                                    presentation.favouriteCurrencies[symbol]
                                        ?: presentation.nonFavouriteCurrencies[symbol]
                                    ).orEmpty() + " ($symbol)",
                        )
                    }

                    Text(
                        text = (ratioForBaseSymbol * value).toString()
                    )
                }
            }
        }
    }
}