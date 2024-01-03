package williankl.accountkt.ui.application.screens.options

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.Github
import compose.icons.feathericons.Sun
import williankl.accountkt.ui.application.BuildKonfig
import williankl.accountkt.ui.application.safeArea.LocalSafeAreaPadding
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.icons.Icon
import williankl.accountkt.ui.design.core.icons.IconData
import williankl.accountkt.ui.design.core.text.CoreText

internal object OptionsBottomSheet : Screen {

    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val viewModel = rememberScreenModel<OptionsBottomSheetViewModel>()
        OptionsContent(
            onGithubOpeningRequested = {
                bottomSheetNavigator.hide()
                viewModel.redirectToGithubPage()
            },
            onThemeToggle = { /* nothing for now */ },
            modifier = Modifier,
        )
    }

    @Composable
    private fun OptionsContent(
        onGithubOpeningRequested: () -> Unit,
        onThemeToggle: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val safeAreaPadding = LocalSafeAreaPadding.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth(),
        ) {
            OptionItem(
                label = "Application Github", // fixme - localized string
                icon = IconData.Vector(
                    imageVector = FeatherIcons.Github,
                    description = null, // fixme - localized description
                ),
                onClick = onGithubOpeningRequested,
            )

            OptionItem(
                label = "Toggle application theme", // fixme - localized string
                icon = IconData.Vector(
                    imageVector = FeatherIcons.Sun,
                    description = null, // fixme - localized description
                ),
                onClick = onThemeToggle,
            )

            CoreText(
                text = "Version ${BuildKonfig.appVersion}", // fixme - localized string
                weight = FontWeight.SemiBold,
                color = KtColor.PrimaryLow,
                modifier = Modifier
                    .padding(top = 12.dp)
            )

            Spacer(
                modifier = Modifier.height(safeAreaPadding.bottomPadding)
            )
        }
    }

    @Composable
    private fun OptionItem(
        label: String,
        icon: IconData,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .clickable { onClick() }
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    )
                    .fillMaxWidth(),
            ) {
                Icon(
                    iconData = icon,
                    modifier = Modifier.size(24.dp),
                )

                CoreText(
                    text = label,
                    weight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(
                modifier = Modifier
                    .background(KtColor.Surface.animatedColor)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}