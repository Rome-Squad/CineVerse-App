package com.giraffe.explore.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.explore.R
import com.giraffe.explore.SearchScreenState

@Composable
fun HistoryAndRecentItems(
    state: SearchScreenState,
    onClickClearAll: () -> Unit,
    onClickItem: (item: String) -> Unit,
    onClickIcon: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SectionTitle(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            title = if (state.isSearchHistoryVisible) stringResource(R.string.history) else stringResource(
                R.string.search_suggestions
            ),
            clickableText = if (state.isSearchHistoryVisible) stringResource(R.string.clear_all) else "",
            onClickableText = onClickClearAll
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(state.resultSearchKeyword) {
                SearchItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = it.keyword,
                    isFromHistory = it.isFromSearchHistory,
                    onClickItem = onClickItem,
                    onClickIcon = onClickIcon,
                    state = state
                )
            }
            if (state.isSearchHistoryVisible) {
                item {
                    MoviesListSection(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        title = stringResource(R.string.you_recent_viewed),
                        movies = state.recentViews
                    )
                }
            }
        }
    }
}

@Preview()
@Composable
private fun HistoryAndRecentItemsPreview() {
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
        HistoryAndRecentItems(
            state = SearchScreenState(),
            onClickClearAll = {},
            onClickItem = {},
            onClickIcon = {}
        )
    }
}