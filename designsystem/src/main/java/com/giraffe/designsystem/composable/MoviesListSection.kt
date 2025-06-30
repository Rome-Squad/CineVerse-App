package com.giraffe.designsystem.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun MoviesListSection(
    title: String,
    movies: List<PosterMovie>,
    modifier: Modifier = Modifier,
    onClickShowMore: () -> Unit = {},
    onClickPoster: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.sm,
                color = Theme.color.shade.primary,
            )
            Text(
                text = stringResource(R.string.show_more),
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.brand.primary,
                modifier = Modifier
                    .clickable(onClick = onClickShowMore)
            )
        }

        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(movies.size) { index ->
                PosterItemVertically(
                    movie = movies[index],
                    modifier = Modifier
                        .width(136.dp)
                        .clickable(onClick = onClickPoster),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMoviesListSection() {
    CineVerseTheme {
        MoviesListSection(
            title = "Popular Movies",
            movies = listOf(
                PosterMovie(
                    title = "The Flash",
                    image = painterResource(R.drawable.poster_image),
                    rating = 7.5f,
                ),
                PosterMovie(
                    title = "The Flash",
                    image = painterResource(R.drawable.poster_image),
                    rating = 7.5f,
                ),
                PosterMovie(
                    title = "The Flash",
                    image = painterResource(R.drawable.poster_image),
                    rating = 7.5f,
                )
            ),
            onClickShowMore = {},
            onClickPoster = {}
        )
    }
}