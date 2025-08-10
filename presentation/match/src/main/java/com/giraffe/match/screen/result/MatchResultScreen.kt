package com.giraffe.match.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.components.HeroCarousel
import com.giraffe.presentation.match.R


@Composable
fun MatchResultScreen(
    navigateBack: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToYouTubePlayer: (String) -> Unit,
) {
    MatchResultContent(
        state = MatchResultScreenState(
            matchItems = listOf(
                MatchResultModel(
                    id = 27205,
                    title = "Inception",
                    posterUrl = "https://image.tmdb.org/t/p/w500/gqby0RhyehP3uRrzmdyUZ0CgPPe.jpg",
                    genres = listOf("Action", "Sci-Fi", "Thriller"),
                    rating = 8.8f,
                    mediaType = MediaType.MOVIE,
                    duration = "2h 28m",
                    releaseDate = "2010-07-16",
                    youtubeVideoId = "mpj9dL7swwk"
                ),
                MatchResultModel(
                    id = 155,
                    title = "The Dark Knight",
                    posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                    genres = listOf("Action", "Crime", "Drama"),
                    rating = 9.0f,
                    mediaType = MediaType.MOVIE,
                    duration = "2h 32m",
                    releaseDate = "2008-07-18",
                    youtubeVideoId = "7pVaGNOp5Vc"
                ),
                MatchResultModel(
                    id = 1396,
                    title = "Breaking Bad",
                    posterUrl = "https://image.tmdb.org/t/p/w500/ggFHVNu6YYI5L9pCfOacjizRGt.jpg",
                    genres = listOf("Crime", "Drama", "Thriller"),
                    rating = 9.5f,
                    mediaType = MediaType.SERIES,
                    duration = "5 Seasons",
                    releaseDate = "2008-01-20",
                    youtubeVideoId = "XZ8daibM3AE"
                ),
                MatchResultModel(
                    id = 66732,
                    title = "Stranger Things",
                    posterUrl = "https://image.tmdb.org/t/p/w500/x2LSRK2Cm7MZhjluni1msVJ3wDF.jpg",
                    genres = listOf("Drama", "Fantasy", "Horror"),
                    rating = 8.7f,
                    mediaType = MediaType.SERIES,
                    duration = "4 Seasons",
                    releaseDate = "2016-07-15",
                    youtubeVideoId = "mnd7sFt5c3A"
                )
            ),
            isLoading = false,
            isError = false
        ),
        navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
        navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
        navigateBack = navigateBack,
        navigateToYouTubePlayer = navigateToYouTubePlayer,
    )

}

@Composable
private fun MatchResultContent(
    state: MatchResultScreenState,
    navigateBack: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToYouTubePlayer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .background(Theme.color.background.screen)
    ) {

        AppBar(
            showBackButton = true,
            title = stringResource(R.string.here_s_your_match_list),
            onBackButtonClick = navigateBack,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        HeroCarousel(
            items = state.matchItems.map { it.posterUrl },
            onPageChanged = { newIndex -> selectedIndex = newIndex },
            onItemClick = { clickedIndex ->
                val clickedItem = state.matchItems.getOrNull(clickedIndex)
                clickedItem?.let {
                    when (it.mediaType) {
                        MediaType.MOVIE -> navigateToMoviesDetailsScreen(it.id)
                        MediaType.SERIES -> navigateToSeriesDetailsScreen(it.id)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val match = state.matchItems.getOrNull(selectedIndex)
        match?.let {
            MainMovieOrSeriesDetails(
                modifier = Modifier.padding(horizontal = 16.dp),
                type = stringResource(id = it.mediaType.labelRes),
                name = it.title,
                genres = it.genres,
                rating = it.rating,
                duration = it.duration,
                releaseDate = it.releaseDate,
                isPlayButtonEnabled = true,
                onClickPlay = { navigateToYouTubePlayer(it.youtubeVideoId) },
                onClickAdd = {},
            )
        }

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            text = stringResource(R.string.view_details),
        ) {
            match?.let {
                when (it.mediaType) {
                    MediaType.MOVIE -> navigateToMoviesDetailsScreen(it.id)
                    MediaType.SERIES -> navigateToSeriesDetailsScreen(it.id)
                }
            }
        }
    }
}


