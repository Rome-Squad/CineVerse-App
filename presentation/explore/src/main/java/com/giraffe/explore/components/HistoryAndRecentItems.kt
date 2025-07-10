package com.giraffe.explore.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.explore.SearchDataClass
import com.giraffe.explore.SearchScreen

@Composable
fun HistoryAndRecentItems(
    value: String,
    isThereHistories: Boolean,
    isThereRecentView: Boolean,
    avilableHistory: Boolean,
    onBackClicked: () -> Unit,
    onChangeValue: (value: String) -> Unit,
) {

    var selectedIndex by remember { mutableIntStateOf(0) }

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


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ExploreHeader(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            showBackButton = true,
            endIcon = painterResource(Theme.icons.outline.microphone),
            tabsTitles = listOf("Movies", "Series", "Actors"),
            selectedTabIndex = selectedIndex,
            onTabClick = { selectedIndex = it },
            onBackClick = onBackClicked,
            onValueChange = onChangeValue,
            value = value
        )
        if (avilableHistory) {
            SectionTitle(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                title = "History",
                clickableText = "Clear All"
            )
        } else {
            SectionTitle(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                title = "You Recent Viewed",
                clickableText = "Clear All"
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(tempList) {
                SearchItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = it.text,
                    isFromHistory = it.isFromHistory,
                    onClick = {}
                )
            }
        }
        if (isThereRecentView) {
            MoviesListSection(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = "You Recent Viewed",
                movies = listOfMovies
            )
        }
    }
}

val tempList = listOf(
    SearchDataClass("alaa", true),
    SearchDataClass("alaa", true),
    SearchDataClass("alaa", true),
    SearchDataClass("alaa", true),
    SearchDataClass("alaa", true),
    SearchDataClass("alaa", true),
    SearchDataClass("alaa", true),
    SearchDataClass("alaa", true)
)

@Preview()
@Composable
private fun HistoryAndRecentItemsPreview() {
    CineVerseTheme {
        HistoryAndRecentItems(
            value = "",
            isThereHistories = true,
            isThereRecentView = true,
            avilableHistory = true,
            onBackClicked = {},
            onChangeValue = {}
        )
    }
}