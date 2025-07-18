package com.giraffe.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun IconTextBox(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = Theme.textStyle.label.md.medium,
    textColor: Color = Theme.color.shade.primary,
    icon: @Composable () -> Unit,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon()

        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewIconTextBox() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        IconTextBox(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.full
                    )
                )
                .background(
                    color = Theme.color.background.card
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            text = "History"
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.outline_history
                ),
                contentDescription = "History",
                tint = Theme.color.brand.primary
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewIconTextBoxDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        IconTextBox(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.full
                    )
                )
                .background(
                    color = Theme.color.background.card
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            text = "History"
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.outline_history
                ),
                contentDescription = "History",
                tint = Theme.color.brand.primary
            )
        }
    }
}
