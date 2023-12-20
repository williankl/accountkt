package williankl.accountkt.ui.design.core.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedColor
import williankl.accountkt.ui.design.core.icons.Icon
import williankl.accountkt.ui.design.core.icons.IconData

@Composable
public fun CoreTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    color: KtColor = KtColor.PrimaryHigh,
    size: TextUnit = TextUnit.Unspecified,
    weight: FontWeight = FontWeight.Normal,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = TextStyle(
            color = color.animatedColor,
            fontSize = size,
            fontWeight = weight,
            fontFamily = FontFamily.Monospace,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = Brush.verticalGradient(
            colors = listOf(KtColor.PrimaryHigh.animatedColor, KtColor.PrimaryLow.animatedColor)
        ),
        decorationBox = decorationBox,
    )
}

@Composable
public fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: IconData? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    color: KtColor = KtColor.PrimaryHigh,
    size: TextUnit = TextUnit.Unspecified,
    weight: FontWeight = FontWeight.Normal,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    val borderColor =
        if (isFocused) KtColor.SecondaryHigh
        else KtColor.Transparent

    CoreTextInput(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .onFocusChanged { state ->
                isFocused = state.isFocused
            },
        enabled = enabled,
        readOnly = readOnly,
        color = color,
        size = size,
        weight = weight,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .border(
                        shape = RoundedCornerShape(8.dp),
                        color = borderColor.animatedColor,
                        width = 1.dp,
                    )
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = KtColor.Secondary.animatedColor(0.5f)
                    )
                    .padding(
                        vertical = 8.dp,
                        horizontal = 12.dp,
                    ),
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.weight(1f),
                    content = {
                        innerTextField()
                    }
                )


                trailingIcon?.let { iconData ->
                    Icon(
                        iconData = iconData,
                        modifier = Modifier,
                        tint = color,
                    )
                }
            }
        }
    )
}