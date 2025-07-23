package com.giraffe.details.screens.seriesdetails.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.details.R
import com.giraffe.details.components.AddToCollectionContent
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
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {

        // Header
        Column(Modifier.padding(horizontal = 16.dp)) {
            AppBar(
                showBackButton = true,
                onBackButtonClick = onBackButtonClick
            )
            MainMovieOrSeriesDetailsAnimatedContent(
                type = TypeOfScreen.SERIES.name,
                name = state.seriesDetails.name,
                rating = state.seriesDetails.rating,
                imageUrl = state.seriesDetails.posterUrl,
                genres = state.genres,
                releaseYear = state.seriesDetails.releaseYear,
                onClickAdd = interaction::onClickAddToCollection,
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
                title = stringResource(R.string.storyline),
                description = state.seriesDetails.overview
            )

            SectionTitle(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.latest_seasons),
                clickableText = stringResource(R.string.show_more),
                onClickableText = { interaction.navigateToSeasonsScreen(state.seriesDetails.id) }
            )

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

            AnimatedVisibility(state.cast.isNotEmpty()) {
                StarCastSection(
                    title = stringResource(R.string.star_cast),
                    onShowMoreClick = {},
                    castList = state.cast,
                    onCastClick = { interaction.navigateToCastDetailsScreen(it) }
                )
            }

            AnimatedVisibility(state.crew.isNotEmpty()) {
                StaffInfoSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = stringResource(R.string.behind_the_scenes),
                    staffList = state.crew
                )
            }

            AnimatedVisibility(state.recommendedSeries.isNotEmpty()) {
                MoviesListSection(
                    title = stringResource(R.string.you_might_also_like),
                    endText = stringResource(R.string.show_more),
                    movies = state.recommendedSeries,
                    onClickEndText = {
                        interaction.navigateToRecommendedSeriesScreen(
                            seriesId = state.seriesDetails.id,
                            title = state.seriesDetails.name
                        )
                    },
                    onClickPoster = { }
                )
            }

            RatingSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClickCard = interaction::onClickGiveStars
            )

            AnimatedVisibility(state.seriesReviews.isNotEmpty()) {
                SectionTitle(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = stringResource(R.string.top_reviews),
                    clickableText = stringResource(R.string.show_more),
                )

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
        }
    }

    BaseBottomSheet(
        isVisible = state.isVisibleAddToCollectionBottomSheet,
        onDismiss = interaction::onDismissAddToCollectionBottomSheet,
        title = stringResource(R.string.add_to_collection),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
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
        onDismiss = interaction::onDismissGiveStarsBottomSheet,
        title = stringResource(R.string.rate_the_movie),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RatingSelector()
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    text = stringResource(R.string.add_to_rate),
                    enabled = false,
                    onClick = {}
                )
            }
        }
    )
}