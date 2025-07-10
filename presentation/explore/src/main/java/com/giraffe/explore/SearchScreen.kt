package com.giraffe.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.explore.components.CastItem
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.HistoryAndRecentItems
import com.giraffe.explore.components.NoResult
import com.giraffe.explore.components.ResultsActors
import com.giraffe.explore.components.ResultsMoviesOrSeriousGrid
import com.giraffe.explore.components.ResultsMoviesOrSeriousList
import com.giraffe.explore.components.SearchItem

@Composable
fun SearchScreen(
    value: String,
    isThereHistories: Boolean,
    isThereRecentView: Boolean,
    avilableHistory: Boolean,
    viewToggle: Boolean,
    onBackClicked: () -> Unit,
//    selectedTapsIndex: () -> Int,
    onChangeValue: (value: String) -> Unit,
) {
    var selectedTapsIndex by remember { mutableIntStateOf(0) }
//    var searchString by remember { mutableIntStateOf(0) }
    var isListSelected by remember { mutableStateOf(false) }
    val searches = listOf(
        SearchItem(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "Batman",
            isFromHistory = true,
            onClick = {}
        ),
        SearchItem(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "Batman",
            isFromHistory = true,
            onClick = {}
        ),
        SearchItem(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "Batman",
            isFromHistory = true,
            onClick = {}
        ),
        SearchItem(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "Batman",
            isFromHistory = true,
            onClick = {}
        ),
        SearchItem(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "Batman",
            isFromHistory = true,
            onClick = {}
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
//        HistoryAndRecentItems(
//            value = value,
//            isThereHistories = isThereHistories,
//            isThereRecentView = isThereRecentView,
//            avilableHistory = avilableHistory,
//            onBackClicked = onBackClicked,
//            onChangeValue = onChangeValue
//        )

        Column() {
            ExploreHeader(
                showBackButton = true,
                endIcon = painterResource(Theme.icons.outline.microphone),
                viewTaps = true,
                tabsTitles = listOf("Movies", "Series", "Actors"),
                selectedTabIndex = selectedTapsIndex,
                onTabClick = { selectedTapsIndex = it },
                onValueChange = {},
                value = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

//                NoResult()
//                ResultsActors()
//                ResultsMoviesOrSeriousGrid()
                ResultsMoviesOrSeriousList()

            }
        }
        if (viewToggle) {
            ViewToggle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                isListSelected = isListSelected,
                onViewToggle = { isListSelected = !isListSelected },
            )
        }


    }
}

@Preview()
@Composable
private fun ExploreScreenPreview() {
    CineVerseTheme {
        SearchScreen(
            value = "",
            isThereHistories = true,
            isThereRecentView = true,
            avilableHistory = true,
            viewToggle = true,
            onBackClicked = {},
            onChangeValue = {}
        )
    }
}