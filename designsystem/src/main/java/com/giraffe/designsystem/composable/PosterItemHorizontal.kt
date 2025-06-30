package com.giraffe.designsystem.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun PosterItemHorizontal(
    movie: PosterMovie,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
    ) {
        AnimatedVisibility(isLoading) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg
                        )
                    )
                    .background(Theme.color.brand.tertiary)
                    .size(width = 64.dp, height = 88.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Theme.icons.dueTone.image),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        AnimatedVisibility(!isLoading) {
            Image(
                painter = movie.image,
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg
                        )
                    )
                    .size(width = 64.dp, height = 88.dp),
                contentScale = ContentScale.Crop
            )
        }


        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 4.dp, top = 12.5.dp, bottom = 12.5.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = movie.title,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.shade.primary
            )

            Text(
                text = movie.genres ?: "Unknown Genre",
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.shade.secondary
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(Theme.icons.dueTone.clock),
                    contentDescription = null,
                    tint = Theme.color.shade.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = movie.time ?: "Unknown Time",
                    style = Theme.textStyle.label.md.regular,
                    color = Theme.color.shade.secondary
                )

                Icon(
                    painter = painterResource(Theme.icons.dueTone.calendar),
                    contentDescription = null,
                    tint = Theme.color.shade.secondary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(16.dp)
                )
                Text(
                    text = movie.date ?: "Unknown Date",
                    style = Theme.textStyle.label.md.regular,
                    color = Theme.color.shade.secondary
                )
            }

        }

        Rating(
            value = movie.rating,
            modifier = Modifier.padding(end = 12.dp, top = 12.dp)
        )
    }
}

@Preview
@Composable
fun PreviewPosterItemHorizontal() {
    CineVerseTheme {
        PosterItemHorizontal(
            movie = PosterMovie(
                title = "The Flash",
                image = painterResource(R.drawable.poster_image),
                rating = 7.5f,
                genres = "Drama, Action, Crime, Thriller",
                time = "2h 32m",
                date = "2008, Jul 18"
            ),
            modifier = Modifier.height(88.dp)
        )
    }
}
@Preview
@Composable
fun PreviewPosterItemHorizontalWhenLoading() {
    CineVerseTheme {
        PosterItemHorizontal(
            movie = PosterMovie(
                title = "The Flash",
                image = painterResource(R.drawable.poster_image),
                rating = 7.5f,
                genres = "Drama, Action, Crime, Thriller",
                time = "2h 32m",
                date = "2008, Jul 18"
            ),
            isLoading = true,
            modifier = Modifier.height(88.dp)
        )
    }
}