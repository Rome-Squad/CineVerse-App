package com.giraffe.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@Composable
fun IconTextBox(
    modifier: Modifier = Modifier,
    icon: Painter,
    contentDescription: String,
    text: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            tint = Theme.color.shade.secondary,
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp)
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
            icon = painterResource(Theme.icons.outline.location),
            contentDescription = stringResource(R.string.location_icon),
            text = stringResource(R.string.place_of_birth, "cairo")
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
            icon = painterResource(Theme.icons.outline.location),
            contentDescription = stringResource(R.string.location_icon),
            text = stringResource(R.string.place_of_birth, "Cairo")
        )
    }
}
