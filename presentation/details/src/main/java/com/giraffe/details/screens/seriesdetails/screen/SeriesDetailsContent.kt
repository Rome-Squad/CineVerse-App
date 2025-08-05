package com.giraffe.details.screens.seriesdetails.screen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.PosterListSection
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.CollectionBottomSheetContent
import com.giraffe.details.components.LoginBottomSheet
import com.giraffe.details.components.MainMovieOrSeriesDetailsAnimatedContent
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.RatingSelector
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.SeasonCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.screens.seriesdetails.SeriesDetailsInteractionListener
import com.giraffe.details.screens.seriesdetails.SeriesDetailsScreenState
import com.giraffe.details.utils.TypeOfScreen
import kotlin.math.min

@Composable
fun SeriesDetailsContent(
    state: SeriesDetailsScreenState,
    interaction: SeriesDetailsInteractionListener,
    onBackButtonClick: () -> Unit,
    onClickPlay: (String) -> Unit,
    navigateToLogIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()
    var imageWidth by remember { mutableIntStateOf(216) }
    var imageHeight by remember { mutableIntStateOf(288) }
    var consumedX by remember { mutableIntStateOf(0) }
    var consumedY by remember { mutableIntStateOf(0) }
    var animationProgress by remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                if (
                    (scrollState.firstVisibleItemIndex != 0 || scrollState.firstVisibleItemScrollOffset != 0)
                    && delta > 0
                ) return Offset(
                    consumedX.toFloat(),
                    consumedY.toFloat()
                )
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
                        onBackButtonClick = onBackButtonClick,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    MainMovieOrSeriesDetailsAnimatedContent(
                        type = stringResource(id = TypeOfScreen.SERIES.titleResId),
                        name = state.seriesDetails.name,
                        imageUrl = state.seriesDetails.posterUrl,
                        rating = state.seriesDetails.rating,
                        genres = state.genres,
                        releaseYear = state.seriesDetails.releaseYear,
                        isPlayButtonEnabled = state.seriesDetails.youtubeVideoId.isNotBlank(),
                        onClickPlay = { onClickPlay(state.seriesDetails.youtubeVideoId) },
                        onClickAdd = interaction::onClickAddToCollection,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        duration = "${state.seasons.size} ${stringResource(R.string.seasons)}",
                        animationProgress = animationProgress
                    )
                }
            }

            if (state.seriesDetails.overview.isNotBlank()) {
                item {
                    InfoSection(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = stringResource(R.string.storyline),
                        description = state.seriesDetails.overview
                    )
                }
            }

            if (state.seasons.isNotEmpty()) {
                item {
                    SectionTitle(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = stringResource(R.string.latest_seasons),
                        clickableText = if (state.seasons.size > 3) stringResource(R.string.show_more) else null,
                        onClickableText = { interaction.navigateToSeasonsScreen(state.seriesDetails.id) }
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
                                year = state.seasons[i].releaseYear
                                    .takeIf { it.isNotBlank() && it.contains("-") }
                                    ?.split("-")
                                    ?.firstOrNull()
                                    ?.toIntOrNull()
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
                        onCastClick = interaction::navigateToCastDetailsScreen
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
                            interaction.navigateToRecommendedSeriesScreen(
                                seriesId = state.seriesDetails.id,
                                title = state.seriesDetails.name
                            )
                        },
                        onClickPoster = {
                            interaction.navigateToSeriesDetails(it.id)
                        }
                    )
                }
            }

            item {
                RatingSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClickCard = interaction::onClickGiveStars
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
                            onClickableText = { interaction.navigateToReviews(state.seriesDetails.id) }
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
            isVisible = state.isVisibleAddToCollectionBottomSheet,
            onDismiss = interaction::onDismissAddToCollectionBottomSheet,
            title = stringResource(R.string.add_to_collection),
            modifier = Modifier.padding(vertical = 28.dp, horizontal = 12.dp),
            content = {
                CollectionBottomSheetContent(
                    onCreateCollectionClick = interaction::onClickAddToCollection
                )
            }
        )

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
                        onClick = interaction::addRate
                    )
                }
            }
        )

        LoginBottomSheet(
            isVisible = state.isVisibleLoginBottomSheet,
            onLogInClick = navigateToLogIn,
            onDismiss = interaction::onDismissGiveStarsBottomSheet
        )
    }
}