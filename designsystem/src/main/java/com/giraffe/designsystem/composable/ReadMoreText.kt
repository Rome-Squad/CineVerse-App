package com.giraffe.designsystem.composable


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun ReadMoreText(
    modifier: Modifier = Modifier,
    text: String,
    readMoreMaxLines: Int = 4,
    readMoreText: String = " Read More",
    readLessText: String = " Show Less",
    readMoreColor: Color = Theme.color.brand.primary,
    textStyle: TextStyle = Theme.textStyle.body.md.medium,
) {
    val (isExpanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }

    ReadMoreText(
        text = text,
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
            .noHoverClickable {
                onExpandedChange(!isExpanded)
            },
        readMoreText = readMoreText,
        readLessText = readLessText,
        readMoreColor = readMoreColor,
        style = textStyle,
        color = Theme.color.shade.primary,
        readMoreMaxLines = readMoreMaxLines,
        readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
        toggleArea = ToggleArea.More
    )
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewReadMoreText() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        ReadMoreText(
            text = "very good",
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewReadMoreTextDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        ReadMoreText(
            text = "very good",
            modifier = Modifier
        )
    }
}
