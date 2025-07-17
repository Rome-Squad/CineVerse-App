package com.giraffe.details.screens.seriesdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.MainMovieOrSeriesDetailsAnimatedContent
import com.giraffe.details.components.SeasonCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.utils.TypeOfDetailsScreen
import com.giraffe.details.utils.imageSourceToPainter
import org.koin.androidx.compose.koinViewModel
import kotlin.math.min

@Composable
fun SeriesDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: SeriesDetailsViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(state.isLoadingSeason) {
            Progress(modifier = Modifier.size(40.dp))
        }
        AnimatedVisibility(!state.isLoadingSeason) {
            SeriesDetailsContent(state = state)
        }
    }
}

@Composable
fun SeriesDetailsContent(
    state: SeriesDetailsScreenState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {

        // Header
        Column(Modifier.padding(horizontal = 16.dp)) {
            AppBar(
                showBackButton = true,
                onBackButtonClick = {}
            )
            MainMovieOrSeriesDetailsAnimatedContent(
                type = TypeOfDetailsScreen.SERIES.name,
                name = state.seriesDetails.name,
                rating = state.seriesDetails.rating,
                image = state.seriesDetails.posterUrl,
                genres = state.genres,
                releaseYear = state.seriesDetails.releaseYear,
                onClickAdd = {},
                onClickPlay = {},
                isScrolled = scrollState.value > 0,
                durationAnimation = 2000
            )
        }

        // Scrolling Body
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(com.giraffe.details.R.string.storyline),
                description = state.seriesDetails.overview
            )


            SectionTitle(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(com.giraffe.details.R.string.latest_seasons),
                clickableText = stringResource(com.giraffe.details.R.string.show_more),
                onClickableText = {}
            )
            AnimatedVisibility(state.seasons.isNotEmpty()) {

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (i in 0..min(2, state.seasons.size - 1)) {
                        SeasonCard(
                            poster = (state.seasons[i].posterUrl
                                ?: R.drawable.main_poster_test).imageSourceToPainter(),
                            title = "Season ${state.seasons[i].seasonNumber + 1}",
                            overview = state.seasons[i].overview,
                            rating = state.seasons[i].rating,
                            episodes = state.seasons[i].episodeCount,
                            year = state.seasons[i].releaseYear.split("-").first().toInt(),
                            onClick = {}
                        )
                    }
                }
            }

            StarCastSection(
                title = stringResource(com.giraffe.details.R.string.star_cast),
                onShowMoreClick = {},
                castList = state.cast
            )

            StaffInfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(com.giraffe.details.R.string.behind_the_scenes),
                staffList = state.crew
            )


            Box(
                Modifier
                    .fillMaxWidth()
                    .height(5000.dp)
            )
        }
    }
}