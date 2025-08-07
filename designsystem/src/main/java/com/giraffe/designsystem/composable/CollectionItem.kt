package com.giraffe.designsystem.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CollectionItem(
    modifier: Modifier = Modifier,
    text: String,
    description: String,
    icon: Int,
    onClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .clickable(onClick = onClick)
            .background(
                color = Theme.color.background.card,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .padding(12.dp)
            .widthIn(min = screenWidth * 0.5f, max = screenWidth * 0.75f),
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
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.shade.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.shade.secondary
            )
        }
        Icon(
            painter = painterResource(Theme.icons.outline.altArrowRight),
            contentDescription = stringResource(R.string.arrow_right_icon),
            tint = Theme.color.shade.tertiary,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(20.dp)
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
            description = "items number",
            icon = R.drawable.due_tone_folder
        )
    }

}