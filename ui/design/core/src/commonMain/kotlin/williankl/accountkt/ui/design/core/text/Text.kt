package williankl.accountkt.ui.design.core.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import williankl.accountkt.ui.design.core.color.KtColor
import williankl.accountkt.ui.design.core.color.animatedComposeColor

@Composable
public fun CoreText(
    text: String,
    modifier: Modifier = Modifier,
    color: KtColor = KtColor.NeutralHigh,
    size: TextUnit = TextUnit.Unspecified,
    weight: FontWeight = FontWeight.Normal,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
) {
    CoreText(
        text = AnnotatedString(text),
        modifier = modifier,
        color = color,
        size = size,
        weight = weight,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
    )
}

@Composable
public fun CoreText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: KtColor = KtColor.NeutralHigh,
    size: TextUnit = TextUnit.Unspecified,
    weight: FontWeight = FontWeight.Normal,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
) {
    val textColor by color.animatedComposeColor

    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontWeight = weight,
            color = textColor,
            fontSize = size,
            fontFamily = FontFamily.Monospace,
        ),
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        color = null,
    )
}