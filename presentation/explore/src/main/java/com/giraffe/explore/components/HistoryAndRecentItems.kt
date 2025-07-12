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
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.R
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.screen.SearchScreenState

@Composable
fun HistoryAndRecentItems(
    state: SearchScreenState,
    onClickClearAll: () -> Unit,
    onClickItem: (item: SearchKeyword) -> Unit,
    onClickIcon: (item: SearchKeyword) -> Unit
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

        if (state.resultSearchKeyword.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                items(state.resultSearchKeyword) { keyWord ->
                    SearchItem(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = keyWord.keyword,
                        isFromHistory = keyWord.isFromSearchHistory,
                        onClickItem = { onClickItem(keyWord) },
                        onClickIcon = { onClickIcon(keyWord) },
                    )
                }
            }
        }

        if (state.recentViews.isNotEmpty()) {
            MoviesListSection(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f),
                endText = stringResource(R.string.clear_all),
                title = stringResource(R.string.you_recent_viewed),
                movies = state.recentViews
            )
        }
    }
}

@Preview()
@Composable
private fun HistoryAndRecentItemsPreview() {
    val listOfMovies: List<Poster> = listOf(
        Poster(
            id = 1,
            name = "The Flash",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 7.5f,
            genres = "Drama, Action, Crime, Thriller",
            time = "2h 32m",
            date = "2008, Jul 18"
        ),
        Poster(
            id = 2,
            name = "The Flash 2",
            imageUri = "https://images.app.goo.gl/sUTYc8FLRCbWJ9Zp6",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 45m",
            date = "2010, Jun 10"
        ),
        Poster(
            id = 3,
            name = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        Poster(
            id = 4,
            name = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        Poster(
            id = 5,
            name = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        Poster(
            id = 6,
            name = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        Poster(
            id = 7,
            name = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        Poster(
            id = 8,
            name = "The Flash 3",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 8.0f,
            genres = "Action, Adventure",
            time = "2h 40m",
            date = "2012, Jun 10"
        ),
        Poster(
            id = 9,
            name = "The Flash 3",
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