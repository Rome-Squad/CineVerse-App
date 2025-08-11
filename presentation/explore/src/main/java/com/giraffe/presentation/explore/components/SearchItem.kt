package com.giraffe.presentation.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme

@Composable
fun SearchItem(
    text: String,
    isRecent: Boolean,
    postfixIcon: Painter,
    onClickItem: (item: String) -> Unit,
    onClickIcon: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp)
) {
    Box(modifier) {
        Row(
            modifier = Modifier
                .clickable { onClickItem(text) }
                .padding(paddingValues)
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = if (isRecent) Theme.icons.outline.history else Theme.icons.outline.search),
                contentDescription = "Search",
                modifier = Modifier.size(20.dp),
                tint = Theme.color.shade.tertiary
            )
            Text(
                text = text,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.shade.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = postfixIcon,
                contentDescription = "Navigate",
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(Theme.radius.xs))
                    .clickable(onClick = onClickIcon),
                tint = Theme.color.shade.tertiary
            )

        }

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .height(1.dp)
                .background(Theme.color.shade.quaternary)
                .align(Alignment.BottomEnd)
        )
    }
}