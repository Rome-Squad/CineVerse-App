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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.components.CastMember
import com.giraffe.details.components.MainMovieOrSeriesDetailsAnimatedContent
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.SeasonCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StaffMember
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
    SeriesDetailsContent(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    )
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
                image = (state.seriesDetails.posterUrl
                    ?: R.drawable.main_poster_test).imageSourceToPainter(),
                genres = state.genres,
                releaseYear = state.seriesDetails.releaseYear,
                onClickAdd = {},
                onClickPlay = {},
                isScrolled = scrollState.value > 0
            )
        }

        // Scrolling Body
        Column(
            modifier = Modifier.verticalScroll(scrollState),
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
                            year = 2008, // state.seasons[i].releaseYear.toInt()
                            onClick = {}
                        )

                    }
                }
            }


            StarCastSection(
                title = "Star Cast",
                onShowMoreClick = {},
                castList = listOf(
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),
                    CastMember(
                        actorName = "Cristian Bale",
                        character = "Bruce Wayne",
                        image = R.drawable.main_poster_test.imageSourceToPainter(),
                    ),

                    ),
            )

            StaffInfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Behind the Scenes",
                staffList = listOf(
                    StaffMember(
                        name = "Bob Kane",
                        role = "Characters"
                    ),
                    StaffMember(
                        name = "Christopher Nolan",
                        role = "Director, Screenplay, Story"
                    ),
                    StaffMember(
                        name = "Jonathan Nolan",
                        role = "Screenplay"
                    ),
                    StaffMember(
                        name = "David S. Goyer",
                        role = "Story"
                    )
                ),
                onShowMoreClick = {}
            )

            MoviesListSection(
                title = "You Might Also Like",
                endText = stringResource(R.string.show_more),
                movies = listOf(
                    Poster(
                        id = 1,
                        name = "The Flash",
                        imageUri = R.drawable.main_poster_test.toString(),
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 2,
                        name = "The Flash",
                        imageUri = R.drawable.main_poster_test.toString(),
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 3,
                        name = "The Flash",
                        imageUri = R.drawable.main_poster_test.toString(),
                        rating = 7.5f,
                    ),
                    Poster(
                        id = 4,
                        name = "The Flash",
                        imageUri = R.drawable.main_poster_test.toString(),
                        rating = 7.5f,
                    ),

                    ),
                onClickEndText = {},
                onClickPoster = {}
            )

            RatingSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                onArrowButtonClick = {}
            )

            SectionTitle(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Top Reviews",
                clickableText = "Show More",
                onClickableText = {}
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(5000.dp)
            )
        }
    }
}