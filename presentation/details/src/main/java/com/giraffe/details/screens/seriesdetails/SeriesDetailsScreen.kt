package com.giraffe.details.screens.seriesdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.AddToCollectionContent
import com.giraffe.details.components.MainMovieOrSeriesDetailsAnimatedContent
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.RatingSelector
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.SeasonCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.utils.TypeOfScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.min

@Composable
fun SeriesDetailsScreen(
    seriesID: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
    viewModel: SeriesDetailsViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(seriesID) {
        launch {  viewModel.loadSeriesDetails(seriesID) }
        launch {  viewModel.loadSeason(seriesID) }
        launch {  viewModel.loadRecommendedSeries(seriesID, 1) }
        launch {  viewModel.loadSeriesReviews(seriesID) }
        launch {  viewModel.loadSeriesPeople(seriesID) }
    }

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
            SeriesDetailsContent(
                state = state,
                onAddToCollectionClick = viewModel::onClickAddToCollection,
                onDismissAddToCollectionBottomSheet = viewModel::onDismissAddToCollectionBottomSheet,
                onGiveStarClick = viewModel::onClickGiveStars,
                onDismissAddRatingBottomSheet = viewModel::onDismissGiveStarsBottomSheet,
            )
        }
    }
}

@Composable
fun SeriesDetailsContent(
    state: SeriesDetailsScreenState,
    onAddToCollectionClick: () -> Unit,
    onDismissAddToCollectionBottomSheet: () -> Unit,
    onGiveStarClick: () -> Unit,
    onDismissAddRatingBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
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
                type = TypeOfScreen.SERIES.name,
                name = state.seriesDetails.name,
                rating = state.seriesDetails.rating,
                image = state.seriesDetails.posterUrl,
                genres = state.genres,
                releaseYear = state.seriesDetails.releaseYear,
                onClickAdd = onAddToCollectionClick,
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
                            posterUrl = state.seasons[i].posterUrl,
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

            MoviesListSection(
                title = stringResource(com.giraffe.details.R.string.you_might_also_like),
                endText = stringResource(com.giraffe.details.R.string.show_more),
                movies = state.recommendedSeries,
                onClickEndText = {},
                onClickPoster = {})

            RatingSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClickCard = onGiveStarClick
            )

            AnimatedVisibility(
                visible = state.seriesReviews.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut()
            ) {
                InfoSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = stringResource(com.giraffe.details.R.string.top_reviews),
                    description = state.seriesDetails.overview
                )
            }

            AnimatedVisibility(state.seriesReviews.isNotEmpty()) {

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (index in 0..min(2, state.seriesReviews.size - 1)) {
                        val review = state.seriesReviews[index]
                        val padding = when (index) {
                            0 -> Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                            1 -> Modifier.padding(horizontal = 16.dp)
                            2 -> Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
                            else -> Modifier
                        }
                        ReviewCard(
                            modifier = padding,
                            rate = review.rating.toInt(),
                            reviewText = review.content,
                            reviewDate = review.createdAt,
                            reviewerImageUrl = review.authorImageUrl,
                            reviewerName = review.authorName,
                            reviewerUsername = review.authorUserName
                        )
                    }
                }
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }

    BaseBottomSheet(
        isVisible = state.isVisibleAddToCollectionBottomSheet,
        onDismiss = onDismissAddToCollectionBottomSheet,
        title = stringResource(com.giraffe.details.R.string.add_to_collection),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 28.dp),
        content = {
            AddToCollectionContent(
                title = "My Favorite TV",
                isLoading = false,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            AddToCollectionContent(
                title = "My WatchLis",
                isLoading = false,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            AddToCollectionContent(
                title = "Cristian Bale Movies",
                isLoading = false,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
        }
    )

    BaseBottomSheet(
        isVisible = state.isVisibleGiveStarsBottomSheet,
        onDismiss = onDismissAddRatingBottomSheet,
        title = stringResource(com.giraffe.details.R.string.rate_the_movie),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 28.dp),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RatingSelector()
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    text = stringResource(com.giraffe.details.R.string.add_to_rate),
                    enabled = false,
                    onClick = {})
            }
        }
    )
}