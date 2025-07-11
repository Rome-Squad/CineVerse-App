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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.PosterMovie

@Composable
fun MoviesListSection(
    title: String,
    movies: List<PosterMovie>,
    modifier: Modifier = Modifier,
    paddingHorizontal: Int = 16,
    onClickShowMore: () -> Unit = {},
    onClickPoster: () -> Unit = {}
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingHorizontal.dp),
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
                modifier = Modifier.clickable(onClick = onClickShowMore)
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = paddingHorizontal.dp)
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
private fun Preview() {
    CineVerseTheme {
        MoviesListSection(
            modifier = Modifier.fillMaxWidth(),
            title = "Popular Movies",
            movies = listOf(
                PosterMovie(
                    title = "The Flash",
                    imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                    rating = 7.5f,
                ),
                PosterMovie(
                    title = "The Flash",
                    imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                    rating = 7.5f,
                ),
                PosterMovie(
                    title = "The Flash",
                    imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                    rating = 7.5f,
                ),
                PosterMovie(
                    title = "The Flash",
                    imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                    rating = 7.5f,
                ),
            ),
            onClickShowMore = {},
            onClickPoster = {})
    }
}