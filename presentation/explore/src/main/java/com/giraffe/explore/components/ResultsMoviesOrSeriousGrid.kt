package com.giraffe.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.PosterItemVertically
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.explore.MediaStateUi
import com.giraffe.explore.toPosterMovie

@Composable
fun ResultsMoviesOrSeriousGrid(
    media: List<MediaStateUi>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = media) { media ->
            PosterItemVertically(movie = media.toPosterMovie())
        }
    }
}

@Preview()
@Composable
private fun ResultsMoviesOrSeriousGridPreview() {
    val listOfMovies: List<PosterMovie> = listOf(
        PosterMovie(
            title = "The Flash",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 7.5f,
            genres = "Drama, Action, Crime, Thriller",
            time = "2h 32m",
            date = "2008, Jul 18"
        ),
        PosterMovie(
            title = "The Flash 2",
            imageUri = "https://images.app.goo.gl/sUTYc8FLRCbWJ9Zp6",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 45m",
            date = "2010, Jun 10"
        ),
        PosterMovie(
            title = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        PosterMovie(
            title = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        PosterMovie(
            title = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        PosterMovie(
            title = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        PosterMovie(
            title = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        PosterMovie(
            title = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        PosterMovie(
            title = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        )
    )

    CineVerseTheme {
        ResultsMoviesOrSeriousGrid(media = emptyList())
    }
}