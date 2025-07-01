package com.giraffe.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun IconTextBox(
    modifier: Modifier = Modifier,
    icon: Painter,
    contentDescription: String,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            tint = Theme.color.shade.secondary,
            painter = icon,
            contentDescription = contentDescription
        )
        Text(
            style = Theme.textStyle.label.md.regular,
            color = Theme.color.shade.secondary,
            text = text
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewIconTextBox() {

    CineVerseTheme (
        isDarkTheme = false
    ) {
        IconTextBox(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewIconTextBoxDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        IconTextBox(
            modifier = Modifier
        )
    }
}
