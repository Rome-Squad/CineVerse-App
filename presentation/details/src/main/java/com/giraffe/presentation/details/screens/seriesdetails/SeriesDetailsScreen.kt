package com.giraffe.presentation.details.screens.seriesdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.PosterListSection
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.components.LoginBottomSheet
import com.giraffe.presentation.details.components.MainMovieOrSeriesDetailsAnimatedContent
import com.giraffe.presentation.details.components.RatingSection
import com.giraffe.presentation.details.components.RatingSelector
import com.giraffe.presentation.details.components.ReviewCard
import com.giraffe.presentation.details.components.SeasonCard
import com.giraffe.presentation.details.components.StaffInfoSection
import com.giraffe.presentation.details.components.StarCastSection
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.TypeOfScreen
import kotlin.math.min

@Composable
fun SeriesDetailsScreen(
    navigateToRecommendedSeries: (seriesID: Int, titleSeries: String) -> Unit,
    navigateToCastDetails: (castID: Int) -> Unit,
    navigateToSeason: (seriesId: Int) -> Unit,
    navigateToSeriesDetails: (seriesId: Int) -> Unit,
    onBackButtonClick: () -> Unit,
    navigateToYouTubePlayer: (String) -> Unit,
    navigateToLogIn: () -> Unit,
    navigateToReviews: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    EventListener(events = viewModel.effect) {
        when (it) {
            is SeriesDetailsEffect.NavigateToCastDetails -> navigateToCastDetails(it.personId)

            is SeriesDetailsEffect.NavigateToSeasons -> navigateToSeason(it.seriesId)

            is SeriesDetailsEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(it.seriesId)

            is SeriesDetailsEffect.NavigateToRecommendedSeries -> {
                navigateToRecommendedSeries(it.seriesId, it.title)
            }

            is SeriesDetailsEffect.NavigateToReviews -> navigateToReviews(it.seriesId)

            is SeriesDetailsEffect.NavigateToLogIn -> navigateToLogIn()

            is SeriesDetailsEffect.OnBackButtonClick -> onBackButtonClick()

            is SeriesDetailsEffect.NavigateToYouTubePlayer -> navigateToYouTubePlayer(it.url)

            is SeriesDetailsEffect.Error -> Unit
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(state.isNetworkError) {
                NoInternetScreen(
                    onRetryClick = viewModel::onRetryClick
                )
            }
            AnimatedVisibility(state.isLoading) {
                Progress(modifier = Modifier.size(40.dp))
            }
        }
        AnimatedVisibility(
            visible = !state.isLoading && !state.isNetworkError,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SeriesDetailsContent(
                state = state,
                interaction = viewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
private fun SeriesDetailsContent(
    state: SeriesDetailsScreenState,
    interaction: SeriesDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    var imageWidth by rememberSaveable { mutableIntStateOf(216) }
    var imageHeight by rememberSaveable { mutableIntStateOf(288) }
    var consumedX by rememberSaveable { mutableIntStateOf(0) }
    var consumedY by rememberSaveable { mutableIntStateOf(0) }
    var animationProgress by rememberSaveable { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                if (
                    (scrollState.firstVisibleItemIndex != 0 || scrollState.firstVisibleItemScrollOffset != 0)
                    && delta > 0
                ) {
                    return Offset(consumedX.toFloat(), consumedY.toFloat())
                }

                val newImageWidth = imageWidth + delta
                val previousImageWidth = imageWidth
                imageWidth = newImageWidth.coerceIn(40, 216)
                val newImageHeight = imageHeight + delta
                val previousImageHeight = imageHeight
                imageHeight = newImageHeight.coerceIn(40, 288)
                animationProgress = 1f - (imageHeight - 40) / 248f
                consumedX = imageWidth - previousImageWidth
                consumedY = imageHeight - previousImageHeight
                return Offset(consumedX.toFloat(), consumedY.toFloat())
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
            .nestedScroll(nestedScrollConnection)
    ) {
        AnimatedVisibility(state.isLoading) {
            Progress(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
            )
        }

        LazyColumn(
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Theme.color.background.screen)
                .fillMaxSize()
        ) {
            stickyHeader {
                Column(modifier.background(Theme.color.background.screen)) {
                    AppBar(
                        showBackButton = true,
                        onBackButtonClick = interaction::onBackButtonClick,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    MainMovieOrSeriesDetailsAnimatedContent(
                        type = stringResource(id = TypeOfScreen.SERIES.titleResId),
                        name = state.seriesUi.name,
                        imageUrl = state.seriesUi.posterUrl,
                        rating = state.seriesUi.rating,
                        genres = state.genres,
                        releaseYear = state.seriesUi.releaseYear.toString(),
                        isPlayButtonEnabled = state.seriesUi.youtubeVideoId.isNotBlank(),
                        onClickPlay = { interaction.onPlayButtonClick(state.seriesUi.youtubeVideoId) },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        duration = "${state.seasons.size} ${stringResource(R.string.seasons)}",
                        animationProgress = animationProgress
                    )
                }
            }

            if (state.seriesUi.overview.isNotBlank()) {
                item {
                    InfoSection(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = stringResource(R.string.storyline),
                        description = state.seriesUi.overview
                    )
                }
            }

            if (state.seasons.isNotEmpty()) {
                item {
                    SectionTitle(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = stringResource(R.string.latest_seasons),
                        clickableText = if (state.seasons.size > 3) stringResource(R.string.show_more) else null,
                        onClickableText = { interaction.onShowMoreSeasonsTextClick(state.seriesUi.id) }
                    )
                }
            }
            if (state.seasons.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (i in 0..min(2, state.seasons.size - 1)) {
                            SeasonCard(
                                posterUrl = state.seasons[i].posterUrl,
                                title = stringResource(
                                    R.string.season,
                                    state.seasons[i].seasonNumber + 1
                                ),
                                overview = state.seasons[i].overview,
                                rating = state.seasons[i].rating,
                                episodes = state.seasons[i].episodeCount,
                                year = if (state.seasons[i].releaseYear != null)
                                    state.seasons[i].releaseYear
                                        .takeIf { it?.isNotBlank() == true && it.contains("-") == true }
                                    ?.split("-")
                                    ?.firstOrNull()
                                    ?.toIntOrNull()
                                else null
                            )
                        }
                    }
                }
            }

            if (state.cast.isNotEmpty()) {
                item {
                    StarCastSection(
                        title = stringResource(R.string.star_cast),
                        castList = state.cast,
                        onCastClick = interaction::onCastCardClick
                    )
                }
            }

            if (state.crew.isNotEmpty()) {
                item {
                    StaffInfoSection(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = stringResource(R.string.behind_the_scenes),
                        staffList = state.crew
                    )
                }
            }

            if (state.recommendedSeries.isNotEmpty()) {
                item {
                    PosterListSection(
                        title = stringResource(R.string.you_might_also_like),
                        endText = stringResource(R.string.show_more),
                        posters = state.recommendedSeries,
                        onClickEndText = {
                            interaction.onShowMoreRecommendedSeriesTextClick(
                                seriesId = state.seriesUi.id,
                                title = state.seriesUi.name
                            )
                        },
                        onClickPoster = {
                            interaction.onSeriesPosterClick(it.id)
                        }
                    )
                }
            }

            item {
                RatingSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClickCard = interaction::onGiveStarsCardClick
                )
            }

            if (state.seriesReviews.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SectionTitle(
                            modifier = Modifier,
                            title = stringResource(R.string.top_reviews),
                            clickableText = if (state.seriesReviews.size > 3) stringResource(R.string.show_more) else null,
                            onClickableText = { interaction.onShowMoreReviewsTextClick(state.seriesUi.id) }
                        )

                        val reviewsToShow = state.seriesReviews.take(3)
                        reviewsToShow.forEach { review ->
                            ReviewCard(
                                rate = review.rating,
                                reviewText = review.content,
                                reviewDate = review.createdAt,
                                reviewerImageUrl = review.authorImageUrl,
                                reviewerName = review.authorName,
                                reviewerUsername = review.authorUserName
                            )
                        }
                    }
                }
            }
        }

        BaseBottomSheet(
            isVisible = state.isVisibleGiveStarsBottomSheet,
            onDismiss = interaction::onDismissGiveStarsBottomSheet,
            title = stringResource(R.string.rate_the_movie),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RatingSelector(
                        rate = state.currentRating,
                        onRateClick = interaction::onRateChange
                    )
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        text = stringResource(R.string.add_to_rate),
                        enabled = state.currentRating > 0,
                        onClick = interaction::onAddRateButtonClick
                    )
                }
            }
        )

        LoginBottomSheet(
            isVisible = state.isVisibleLoginBottomSheet,
            onLogInClick = interaction::onLoginButtonClick,
            onDismiss = interaction::onDismissGiveStarsBottomSheet
        )
    }
}