package com.giraffe.details.components.recomended

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.R

@Composable
fun RecommendedContent(
    //  state: RecommendedScreenState,
    modifier: Modifier = Modifier,
) {
    Box {
        LazyColumn(
            modifier = modifier
                .background(Theme.color.background.screen)
                .statusBarsPadding()
                .systemBarsPadding(),
        ) {
            item {
                AppBar(
                    title = "The Dark Night",
                    //state.title,
                    caption = stringResource(R.string.because_you_watched),
                    showBackButton = true,
                    modifier = Modifier.padding(16.dp),
                    onBackButtonClick = {},
                )
            }

            item {
                CardsSection(
                    modifier = Modifier.fillParentMaxHeight(), posters = samplePosters,
                    //state.selectedPosters,
                    isGridSelected = true
                )

            }
        }

        ViewToggle(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, end = 16.dp), isListSelected = false,
            //!state.isGridSelected,
            onGridSelected = {}
            // interactions::onViewChanged,
        )
    }
}


@Composable
private fun CardsSection(
    modifier: Modifier = Modifier,
    posters: List<Poster>,
    isGridSelected: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        TransitionLazyColumnToGrid(
            poster = posters,
            isListSelected = !isGridSelected,
            contentPadding = PaddingValues(vertical = 5.dp),
        )

    }
}

@Preview
@Composable
fun RecommendedContentPreview() {

    RecommendedContent(
        //  state = TODO(),
        // modifier = TODO()
    )
}

val samplePosters = listOf(
    Poster(
        id = 1,
        name = "Inception",
        imageUri = "https://example.com/inception.jpg",
        rating = 4.8f,
        genres = "Action, Sci-Fi",
        time = "2h 28m",
        date = "2010"
    ), Poster(
        id = 2,
        name = "The Dark Knight",
        imageUri = "https://example.com/darkknight.jpg",
        rating = 4.9f,
        genres = "Action, Drama",
        time = "2h 32m",
        date = "2008"
    ), Poster(
        id = 3,
        name = "Interstellar",
        imageUri = "https://example.com/interstellar.jpg",
        rating = 4.7f,
        genres = "Adventure, Drama, Sci-Fi",
        time = "2h 49m",
        date = "2014"
    ), Poster(
        id = 4,
        name = "Tenet",
        imageUri = "https://example.com/tenet.jpg",
        rating = 4.3f,
        genres = "Action, Sci-Fi",
        time = "2h 30m",
        date = "2020"
    )
)
