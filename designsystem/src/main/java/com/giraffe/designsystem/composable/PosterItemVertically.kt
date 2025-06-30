package com.giraffe.designsystem.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun PosterItemVertically(
    movie: PosterMovie,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        AnimatedVisibility(isLoading) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(Theme.radius.lg))
                    .background(Theme.color.background.card)
                    .aspectRatio(136f / 182f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Theme.icons.dueTone.image),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Rating(
                    value = movie.rating,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 8.dp)
                )
            }
        }

        AnimatedVisibility(!isLoading) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(Theme.radius.lg))
                    .background(Theme.color.background.card)
                    .aspectRatio(136f / 182f)
            ) {
                Image(
                    painter = movie.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Rating(
                    value = movie.rating,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 8.dp)
                )
            }
        }

        Text(
            text = movie.title,
            style = Theme.textStyle.body.md.medium,
            color = Theme.color.shade.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPosterItemVertically() {
    CineVerseTheme {
        PosterItemVertically(
            movie = PosterMovie(
                title = "The Flash",
                image = painterResource(R.drawable.poster_image),
                rating = 7.5f,
            ),
            modifier = Modifier.width(156.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPosterItemVerticallyWhenLoading() {
    CineVerseTheme {
        PosterItemVertically(
            movie = PosterMovie(
                title = "The Flash",
                image = painterResource(R.drawable.poster_image),
                rating = 7.5f,
            ),
            isLoading = true,
            modifier = Modifier.width(156.dp)
        )
    }
}