package com.giraffe.details.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MinimizedInfoRow(
    poster: Painter,
    name: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClickPlay: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val key = "_KEY"
    with(sharedTransitionScope) {
        Box(modifier.background(Theme.color.background.screen)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 100.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = poster,
                    contentDescription = null,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = poster.toString() + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(40.dp)
                        .clip(RoundedCornerShape(Theme.radius.full)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    name,
                    style = Theme.textStyle.title.sm,
                    color = Theme.color.shade.primary,
                    modifier = Modifier.sharedElement(
                        sharedContentState = rememberSharedContentState(key = name + key),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
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
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = Theme.icons.dueTone.add.toString() + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(40.dp)
                        .clip(RoundedCornerShape(Theme.radius.md))
                        .background(Theme.color.button.secondary)
                        .clickable(onClick = onClickAdd)
                        .padding(10.dp)
                )
                Icon(
                    painter = painterResource(Theme.icons.dueTone.play),
                    contentDescription = null,
                    tint = Theme.color.brand.tertiary,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = Theme.icons.dueTone.play.toString() + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(40.dp)
                        .clip(RoundedCornerShape(Theme.radius.md))
                        .background(Theme.color.button.primary)
                        .clickable(onClick = onClickPlay)
                        .padding(10.dp)
                )

            }
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
//        MinimizedInfoRow(
//            poster = R.drawable.main_poster_test.imageSourceToPainter(),
//            title = "The Dark Knight",
//            onClickPlay = {},
//            onClickAdd = {},
//            modifier = Modifier.padding(bottom = 12.dp)
//        )
    }
}