package com.giraffe.presentation.details.screens.seriesdetails

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.PosterListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.base.BaseScreen
import com.giraffe.presentation.details.components.GiveStarsBottomSheet
import com.giraffe.presentation.details.components.LoginBottomSheet
import com.giraffe.presentation.details.components.MainMovieOrSeriesDetails
import com.giraffe.presentation.details.components.RatingSection
import com.giraffe.presentation.details.components.ReviewCard
import com.giraffe.presentation.details.components.SeasonCard
import com.giraffe.presentation.details.components.StaffInfoSection
import com.giraffe.presentation.details.components.StarCastSection
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.TypeOfScreen
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource
import kotlinx.coroutines.launch
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
    viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
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

            is SeriesDetailsEffect.Error -> context.showToast(
                it.error.toStringResource()
            )
        }
    }


    SeriesDetailsContent(
        state = state,
        interaction = viewModel,
    )
}


@Composable
private fun SeriesDetailsContent(
    state: SeriesDetailsScreenState,
    interaction: SeriesDetailsInteractionListener,
) {
    val scrollState = rememberLazyListState()
    var imageWidth by rememberSaveable { mutableIntStateOf(216) }
    var imageHeight by rememberSaveable { mutableIntStateOf(288) }
    var consumedX by rememberSaveable { mutableIntStateOf(0) }
    var consumedY by rememberSaveable { mutableIntStateOf(0) }

    val animationProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var lastDelta by remember { mutableIntStateOf(0) }

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


                val progress = 1f - (imageHeight - 40) / 248f
                scope.launch {
                    animationProgress.snapTo(progress.coerceIn(0f, 1f))
                }

                consumedX = imageWidth - previousImageWidth
                consumedY = imageHeight - previousImageHeight

                if (delta != 0) {
                    lastDelta = delta
                }

                return Offset(consumedX.toFloat(), consumedY.toFloat())
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val target = if (lastDelta < 0f) 1f else 0f
                animationProgress.animateTo(
                    target,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearEasing
                    )
                )
                return super.onPostFling(consumed, available)
            }
        }
    }

    BaseScreen(
        title = "",
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onBackClick = interaction::onBackClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
            ) {

                if (state.seriesUi.name.isNotBlank()) {
                    stickyHeader {
                        MainMovieOrSeriesDetails(
                            type = stringResource(id = TypeOfScreen.SERIES.titleResId),
                            posterUrl = state.seriesUi.posterUrl,
                            name = state.seriesUi.name,
                            genres = state.genres,
                            rating = state.seriesUi.rating,
                            duration = "${state.seasons.size} ${stringResource(R.string.seasons)}",
                            releaseDate = state.seriesUi.releaseYear,
                            onClickPlay = { interaction.onPlayButtonClick(state.seriesUi.youtubeVideoId) },
                            isPlayButtonEnabled = state.seriesUi.youtubeVideoId.isNotBlank(),
                            animationProgress = animationProgress,
                            modifier = Modifier
                                .background(Theme.color.background.screen)
                                .padding(top = 16.dp * (1f - animationProgress.value))
                                .padding(horizontal = 16.dp)
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
                                    year = if (state.seasons[i].releaseYear.isNotBlank())
                                        state.seasons[i].releaseYear
                                            .takeIf { it.isNotBlank() && it.contains("-") }
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
                        rate = state.seriesUi.userRating.toInt(),
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

            GiveStarsBottomSheet(
                isVisible = state.isVisibleGiveStarsBottomSheet,
                onDismiss = interaction::onDismissGiveStarsBottomSheet,
                currentRating = state.currentRating,
                onRateChange = interaction::onRateChange,
                onAddRateButtonClick = interaction::onAddRateButtonClick,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                title = stringResource(R.string.rate_the_series)
            )

            LoginBottomSheet(
                isVisible = state.isVisibleLoginBottomSheet,
                onLogInClick = interaction::onLoginButtonClick,
                onDismiss = interaction::onDismissGiveStarsBottomSheet
            )
        }
    }

}