package com.giraffe.cineverseapp.components


import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.presentation.designsystem.theme.CinVerseTheme
import com.giraffe.presentation.designsystem.theme.Theme
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun ReadMoreText(
    modifier: Modifier = Modifier,
    text: String,
    readMoreMaxLines: Int = 4,
    readMoreText: String = " Read More",
    readMoreColor: Color = Theme.color.brand.primary,
    textStyle: TextStyle = Theme.textStyle.body.medium.medium,
) {
    val (isExpanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }

    ReadMoreText(
        text = text,
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
            .clickable {
                onExpandedChange(!isExpanded)
            },
        readMoreText = readMoreText,
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

    CinVerseTheme(
        isDarkTheme = false
    ) {
        ReadMoreText(
            text = "",

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

    CinVerseTheme(
        isDarkTheme = true
    ) {
        ReadMoreText(
            text = "",
            modifier = Modifier
        )
    }
}
