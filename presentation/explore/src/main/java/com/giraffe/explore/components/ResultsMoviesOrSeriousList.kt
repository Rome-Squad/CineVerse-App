package com.giraffe.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.uimodel.PosterMovie

@Composable
fun ResultsMoviesOrSeriousList() {

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(listOfMovies) { movie ->
            PosterItemHorizontal(
                movie = movie,
            )
        }
    }
}

@Preview()
@Composable
private fun ResultsMoviesOrSeriousListPreview() {
    CineVerseTheme {
        ResultsMoviesOrSeriousList()
    }
}