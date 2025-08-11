package com.giraffe.presentation.details.screens.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.giraffe.presentation.details.components.StaffInfoSection
import com.giraffe.presentation.details.components.StarCastSection
import com.giraffe.presentation.details.components.collectionBottomSheet.MovieCollectionsBottomSheet
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.TypeOfScreen
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource

@Composable
fun MovieDetailsScreen(
    navigateToReviews: (Int) -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onClickPlay: (String) -> Unit,
    onClickPoster: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    navigateToMoviesRecommended: (Int, String) -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    EventListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is MovieDetailsEffect.NavigateToReviews -> navigateToReviews(effect.movieId)
            is MovieDetailsEffect.NavigateToCastDetails -> navigateToCastDetails(effect.personId)
            is MovieDetailsEffect.NavigateToMoviesRecommended -> {
                navigateToMoviesRecommended(effect.movieId, effect.title)
            }

            is MovieDetailsEffect.NavigateToLogin -> navigateToLogin()
            is MovieDetailsEffect.NavigateToYouTubePlayer -> onClickPlay(effect.url)
            is MovieDetailsEffect.NavigateToMovieDetails -> onClickPoster(effect.id)
            is MovieDetailsEffect.NavigateBack -> onBackButtonClick()
            is MovieDetailsEffect.Error -> context.showToast(
                effect.error.toStringResource()
            )
        }
    }


    MovieDetailsContent(
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun MovieDetailsContent(
    state: MovieDetailsScreenState,
    interaction: MovieDetailsInteractionListener
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

    BaseScreen(
        title = "",
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onBackClick = interaction::onBackButtonClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {

            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
            ) {

                if (state.movie.title.isNotBlank()) {
                    stickyHeader {
                        MainMovieOrSeriesDetails(
                            type = stringResource(id = TypeOfScreen.MOVIE.titleResId),
                            posterUrl = state.movie.posterUrl,
                            name = state.movie.title,
                            genres = state.movieGenres,
                            rating = state.movie.rating,
                            duration = state.movie.duration,
                            releaseDate = state.movie.releaseYear.orEmpty(),
                            onClickAdd = interaction::onShowAddToCollectionBottomSheet,
                            onClickPlay = { interaction.onPlayButtonClick(state.movie.youtubeVideoId) },
                            isPlayButtonEnabled = state.movie.youtubeVideoId.isNotBlank(),
                            animationProgress = animationProgress,
                            modifier = Modifier
                                .background(Theme.color.background.screen)
                                .padding(horizontal = 16.dp)
                                .padding(top = 16.dp * (1f - animationProgress))
                        )
                    }
                }

                if (state.movie.description.isNotBlank()) {
                    item {
                        InfoSection(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            title = stringResource(R.string.storyline),
                            description = state.movie.description
                        )
                    }
                }

                if (state.cast.isNotEmpty()) {
                    item {
                        StarCastSection(
                            title = stringResource(R.string.star_cast),
                            onCastClick = { interaction.onCastCardClick(it) },
                            castList = state.cast
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

                if (state.recommendedMovies.isNotEmpty()) {
                    item {
                        PosterListSection(
                            title = stringResource(R.string.you_might_also_like),
                            endText = stringResource(R.string.show_more),
                            posters = state.recommendedMovies,
                            onClickEndText = {
                                interaction.onShowMoreRecommendedMoviesTextClick(
                                    state.movie.id,
                                    state.movie.title
                                )
                            },
                            onClickPoster = { interaction.onMoviePosterClick(it.id) }
                        )
                    }
                }

                item {
                    RatingSection(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClickCard = interaction::onGiveStarsCardClick
                    )
                }

                if (state.movieReviews.isNotEmpty()) {
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
                                clickableText = if (state.movieReviews.size > 3) stringResource(
                                    R.string.show_more
                                ) else null,
                                onClickableText = { interaction.onShowMoreReviewsTextClick(state.movie.id) }
                            )

                            val reviewsToShow = state.movieReviews.take(3)
                            reviewsToShow.fastForEach { review ->
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

            MovieCollectionsBottomSheet(
                isVisible = state.collectionBottomSheet != null,
                onDismiss = interaction::onDismissAddToCollectionBottomSheet,
                targetState = state.collectionBottomSheet,
                collections = state.collections,
                onNewCollectionClick = interaction::onCreateCollectionButtonClick,
                onCollectionClick = interaction::onCollectionClick,
                onCreateCollectionButtonClick = interaction::onCreateCollectionButtonClick,
                newCollectionName = state.newCollectionName,
                onNewCollectionNameChange = interaction::onNewCollectionNameChange,
                onConfirmCreateNewCollectionClick = interaction::onConfirmCreateNewCollectionClick,
                onCancelCreateNewCollectionClick = interaction::onCancelCreateNewCollectionClick,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
            )

            GiveStarsBottomSheet(
                isVisible = state.isVisibleGiveStarsBottomSheet,
                onDismiss = interaction::onDismissGiveStarsBottomSheet,
                currentRating = state.currentRating,
                onRateChange = interaction::onRateChange,
                onAddRateButtonClick = interaction::onAddRateButtonClick,
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    ),
            )

            LoginBottomSheet(
                isVisible = state.isVisibleLoginBottomSheet,
                onLogInClick = interaction::onLoginButtonClick,
                onDismiss = interaction::onDismissLoginBottomSheet
            )
        }
    }
}