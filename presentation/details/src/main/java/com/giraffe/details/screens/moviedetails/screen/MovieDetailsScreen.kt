package com.giraffe.details.screens.moviedetails.screen

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.NoInternetScreen
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
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.screens.moviedetails.MovieDetailsEffect
import com.giraffe.details.screens.moviedetails.MovieDetailsInteractionListener
import com.giraffe.details.screens.moviedetails.MovieDetailsScreenState
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.details.utils.TypeOfScreen

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

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MovieDetailsEffect.NavigateToReviews -> navigateToReviews(effect.movieId)
                is MovieDetailsEffect.NavigateToCollection -> {}

                is MovieDetailsEffect.NavigateToCastDetails -> navigateToCastDetails(effect.personId)
                is MovieDetailsEffect.NavigateToMoviesRecommended -> {
                    navigateToMoviesRecommended(effect.movieId, effect.title)
                }

                is MovieDetailsEffect.NavigateToLogin -> navigateToLogin()
                is MovieDetailsEffect.NavigateToYouTubePlayer -> onClickPlay(effect.url)
                is MovieDetailsEffect.NavigateToMovieDetails -> onClickPoster(effect.id)
                is MovieDetailsEffect.NavigateUp -> onBackButtonClick()
                is MovieDetailsEffect.Error -> {}
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(state.isNetworkError) {
            NoInternetScreen(
                onRetryClick = {
                    viewModel.loadMovieDetailsScreen()
                }
            )
        }
        AnimatedVisibility(state.isLoadingMovieDetails) {
            Progress(modifier = Modifier.size(40.dp))
        }
    }
    AnimatedVisibility(
        visible = !state.isLoadingMovieDetails && !state.isNetworkError,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        MovieDetailsContent(
            modifier = Modifier.fillMaxSize(),
            state = state,
            interaction = viewModel
        )
    }
}

@Composable
private fun MovieDetailsContent(
    modifier: Modifier,
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
            .nestedScroll(nestedScrollConnection)
    ) {

        AnimatedVisibility(
            visible = !state.isLoadingMovieDetails,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
            ) {
                stickyHeader {
                    Column(Modifier.background(Theme.color.background.screen)) {
                        AppBar(
                            showBackButton = true,
                            onBackButtonClick = interaction::onBackButtonClick,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        MainMovieOrSeriesDetailsAnimatedContent(
                            type = stringResource(id = TypeOfScreen.MOVIE.titleResId),
                            name = state.movie.title,
                            imageUrl = state.movie.posterUrl,
                            rating = state.movie.rating,
                            genres = state.movieGenres,
                            releaseYear = state.movie.releaseYear.orEmpty(),
                            isPlayButtonEnabled = state.movie.youtubeVideoId.isNotBlank(),
                            onClickPlay = { interaction.onPlayButtonClick(state.movie.youtubeVideoId) },
                            onClickAdd = interaction::onShowAddToCollectionBottomSheet,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            duration = state.movie.duration,
                            animationProgress = animationProgress
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
        }


        BaseBottomSheet(
            isVisible = state.isVisibleAddToCollectionBottomSheet,
            onDismiss = interaction::onDismissAddToCollectionBottomSheet,
            title = stringResource(R.string.add_to_collection),
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            content = {
                CollectionBottomSheetContent(
                    onCreateCollectionClick = interaction::onAddToCollectionButtonClick
                )
            },
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
                        onClick = interaction::onAddRateButtonClick
                    )
                }
            }
        )

        LoginBottomSheet(
            isVisible = state.isVisibleLoginBottomSheet,
            onLogInClick =interaction::onLoginClick,
            onDismiss = interaction::onDismissLoginBottomSheet
        )
    }
}