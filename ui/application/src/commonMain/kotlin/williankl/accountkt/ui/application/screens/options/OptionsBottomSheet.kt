package williankl.accountkt.ui.application.screens.options

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Github
import williankl.accountkt.ui.application.BuildKonfig
import williankl.accountkt.ui.application.LocalApplicationStrings
import williankl.accountkt.ui.application.safeArea.LocalSafeAreaPadding
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.color.theme.LocalKtTheme
import williankl.accountkt.ui.design.core.color.theme.iconVector
import williankl.accountkt.ui.design.core.color.theme.toggleMode
import williankl.accountkt.ui.design.core.icons.Icon
import williankl.accountkt.ui.design.core.icons.IconData
import williankl.accountkt.ui.design.core.text.CoreText

internal object OptionsBottomSheet : Screen {

    @Composable
    override fun Content() {
        val themeHandler = LocalKtTheme.current
        val isSystemInDarkMode = isSystemInDarkTheme()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val viewModel = rememberScreenModel<OptionsBottomSheetViewModel>()

        OptionsContent(
            onGithubOpeningRequested = {
                bottomSheetNavigator.hide()
                viewModel.redirectToGithubPage()
            },
            onThemeToggle = {
                val selectedTheme = themeHandler.currentTheme.toggleMode(isSystemInDarkMode)
                themeHandler.setTheme(selectedTheme)
            },
            modifier = Modifier,
        )
    }

    @Composable
    private fun OptionsContent(
        onGithubOpeningRequested: () -> Unit,
        onThemeToggle: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val settingsStrings = LocalApplicationStrings.current.settingsStrings
        val safeAreaPadding = LocalSafeAreaPadding.current
        val themeHandler = LocalKtTheme.current
        val isSystemInDarkMode = isSystemInDarkTheme()
        val currentThemeIcon = remember(isSystemInDarkMode, themeHandler.currentTheme) {
            themeHandler.currentTheme.iconVector(isSystemInDarkMode)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth(),
        ) {
            OptionItem(
                label = settingsStrings.githubLabel,
                icon = IconData.Vector(
                    imageVector = EvaIcons.Fill.Github,
                    description = settingsStrings.githubIconDescription,
                ),
                onClick = onGithubOpeningRequested,
            )

            OptionItem(
                label = settingsStrings.themeToggleLabel,
                icon = IconData.Vector(
                    imageVector = currentThemeIcon,
                    description = settingsStrings.themeToggleIconDescription,
                ),
                onClick = onThemeToggle,
            )

            CoreText(
                text = settingsStrings.versionLabel(BuildKonfig.appVersion),
                weight = FontWeight.SemiBold,
                color = KtColor.NeutralLow,
                modifier = Modifier
                    .padding(vertical = 24.dp)
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
                AnimatedContent(
                    targetState = icon,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    content = {
                        Icon(
                            iconData = icon,
                            modifier = Modifier.size(24.dp),
                        )
                    }
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