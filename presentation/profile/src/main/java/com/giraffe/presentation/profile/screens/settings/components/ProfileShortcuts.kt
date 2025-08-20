package com.giraffe.presentation.profile.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.model.ProfileRowItem

@Composable
fun ProfileShortcuts(
    modifier: Modifier = Modifier,
    onNavigateToMyCollections: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToRatings: () -> Unit
) {
    val items = listOf(
        ProfileRowItem(
            textResId = com.giraffe.designsystem.R.string.history,
            iconResId = com.giraffe.designsystem.R.drawable.outline_history,
            onClick = onNavigateToHistory
        ),
        ProfileRowItem(
            textResId = com.giraffe.designsystem.R.string.my_collections,
            iconResId = com.giraffe.designsystem.R.drawable.due_tone_video_library,
            onClick = onNavigateToMyCollections
        ),
        ProfileRowItem(
            textResId = com.giraffe.designsystem.R.string.my_ratings,
            iconResId = com.giraffe.designsystem.R.drawable.due_tone_star,
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
                    tint = Theme.color.brand.primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun IconTextBox(
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