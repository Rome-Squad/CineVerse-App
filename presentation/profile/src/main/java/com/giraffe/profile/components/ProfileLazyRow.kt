package com.giraffe.profile.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


data class ProfileRowItem(
    @param:StringRes val textResId: Int,
    @param:DrawableRes val iconResId: Int,
    val onClick: () -> Unit
)

@Composable
fun ProfileLazyRow(
    modifier: Modifier = Modifier,
    onNavigateToMyCollections: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToRatings: () -> Unit
) {

    val items = listOf(
        ProfileRowItem(
            textResId = R.string.history,
            iconResId = R.drawable.outline_history,
            onClick = onNavigateToHistory
        ),
        ProfileRowItem(
            textResId = R.string.my_collections,
            iconResId = R.drawable.due_tone_video_library,
            onClick = onNavigateToMyCollections
        ),
        ProfileRowItem(
            textResId = R.string.my_ratings,
            iconResId = R.drawable.due_tone_star,
            onClick = onNavigateToRatings
        )
    )

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(items) { item ->
            IconTextBox(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = Theme.radius.full))
                    .clickable(
                        onClick = { item.onClick() }
                    )
                    .background(color = Theme.color.background.card)
                    .padding(vertical = 8.dp)
                    .padding(start = 10.dp, end = 12.dp),
                text = stringResource(id = item.textResId)
            ) {
                Icon(
                    painter = painterResource(id = item.iconResId),
                    contentDescription = stringResource(id = item.textResId),
                    tint = Theme.color.brand.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileLazyRowPreview() {
    CineVerseTheme(isDarkTheme = true) {
        ProfileLazyRow(
            onNavigateToMyCollections = {  },
            onNavigateToHistory = {  },
            onNavigateToRatings = {  }
        )
    }
}