package com.giraffe.details.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun CollectionItem(
    text: String,
    icon: Int,
    modifier: Modifier = Modifier
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Row(
        modifier = modifier
            .background(
                color = Theme.color.background.bottomSheetCard,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.collection_item_icon),
            tint = Theme.color.brand.primary,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(20.dp)
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.shade.primary,
                modifier = Modifier.padding(bottom = 4.dp),
            )
        }

        Icon(
            painter = painterResource(Theme.icons.outline.altArrowRight),
            contentDescription = stringResource(R.string.arrow_right_icon),
            tint = Theme.color.shade.tertiary,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(20.dp)
                .graphicsLayer {
                    scaleX = if (isRtl) -1f else 1f
                }
        )
    }
}

@Preview(locale = "ar")
@Composable
private fun Preview() {
    CineVerseTheme(
        isDarkTheme = true,
    ) {
        CollectionItem(
            text = "Title",
            icon = R.drawable.due_tone_folder
        )
    }
}