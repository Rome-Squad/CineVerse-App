package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.utils.imageSourceToPainter

@Composable
fun MinimizedInfoRow(
    poster: Painter,
    title: String,
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit = {},
    onClickAdd: () -> Unit = {}
) {
    Box(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = poster,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.full)),
                contentScale = ContentScale.Crop
            )
            Text(
                title,
                style = Theme.textStyle.title.sm,
                color = Theme.color.shade.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(Theme.icons.dueTone.add),
                contentDescription = null,
                tint = Theme.color.shade.primary,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.color.button.secondary)
                    .padding(10.dp)
                    .clickable(onClick = onClickAdd)
            )
            Icon(
                painter = painterResource(Theme.icons.dueTone.play),
                contentDescription = null,
                tint = Theme.color.brand.tertiary,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.color.button.primary)
                    .padding(10.dp)
                    .clickable(onClick = onClickPlay)
            )

        }
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFF121321
)
fun PreviewMinimizedInfoRow() {
    CineVerseTheme(isDarkTheme = true) {
        MinimizedInfoRow(
            poster = imageSourceToPainter(R.drawable.main_poster_test),
            title = "The Dark Knight",
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}