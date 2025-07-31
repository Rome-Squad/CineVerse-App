package com.giraffe.details.screens.seriesdetails.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.PosterListSection
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
    navigateToLogIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isScrollingUp by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberLazyListState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                isScrollingUp = available.y < 0 || scrollState.firstVisibleItemScrollOffset > 0
                return Offset.Zero
            }
        }
    }
    LazyColumn(
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Theme.color.background.screen)
            .fillMaxSize()
            .systemBarsPadding()
            .nestedScroll(nestedScrollConnection),
    ) {
        stickyHeader {
            Column(
                modifier = modifier
                    .background(Theme.color.background.screen)
            ) {
                AppBar(
                    showBackButton = true,
                    onBackButtonClick = onBackButtonClick,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                MainMovieOrSeriesDetailsAnimatedContent(
                    type = TypeOfScreen.SERIES.name,
                    name = state.seriesDetails.name,
                    rating = state.seriesDetails.rating,
                    imageUrl = state.seriesDetails.posterUrl,
                    genres = state.genres,
                    duration = "${state.seasons.size} ${stringResource(R.string.seasons)}",
                    releaseYear = state.seriesDetails.releaseYear,
                    onClickAdd = interaction::onClickAddToCollection,
                    onClickPlay = {},
                    isScrolled = isScrollingUp,
                    durationAnimation = 400,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        item {
            InfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.storyline),
                description = state.seriesDetails.overview
            )
        }
        item {
            SectionTitle(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.latest_seasons),
                clickableText = if (state.seasons.size > 3) stringResource(R.string.show_more) else null,
                onClickableText = { interaction.navigateToSeasonsScreen(state.seriesDetails.id) }
            )
        }
        item {
            AnimatedVisibility(state.seasons.isNotEmpty()) {
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
        item {
            StarCastSection(
                title = stringResource(R.string.star_cast),
                castList = state.cast,
                onCastClick = interaction::navigateToCastDetailsScreen
            )
        }
        item {
            StaffInfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.behind_the_scenes),
                staffList = state.crew
            )
        }
        item {
            AnimatedVisibility(state.recommendedSeries.isNotEmpty()) {
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
        item {
            AnimatedVisibility(state.seriesReviews.isNotEmpty()) {

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SectionTitle(
                        modifier = Modifier,
                        title = stringResource(R.string.top_reviews),
                        clickableText = stringResource(R.string.show_more),
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