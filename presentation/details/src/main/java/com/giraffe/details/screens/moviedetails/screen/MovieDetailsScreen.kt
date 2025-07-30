package com.giraffe.details.screens.moviedetails.screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.AddToCollectionContent
import com.giraffe.details.components.MainMovieOrSeriesDetailsAnimatedContent
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.RatingSelector
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.screens.moviedetails.MovieDetailsEffect
import com.giraffe.details.screens.moviedetails.MovieDetailsInteractionListener
import com.giraffe.details.screens.moviedetails.MovieDetailsScreenState
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.details.utils.TypeOfScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.min


@Composable
fun MovieDetailsScreen(
    movieID: Int,
    modifier: Modifier = Modifier,
    navigateToReviews: (Int) -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    viewModel: MovieDetailsViewModel = koinViewModel(parameters = { parametersOf(movieID) }),
    onClickPlay: () -> Unit,
    onClickPoster: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    navigateToMoviesRecommended: (Int, String) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MovieDetailsEffect.NavigateToMovies -> {
                    //navController.navigateToMovieDetails(movieID)
                }

                is MovieDetailsEffect.NavigateToReviews -> {
                    navigateToReviews(effect.movieId)
                }

                is MovieDetailsEffect.Error -> {}
                is MovieDetailsEffect.NavigateToCollection -> {}
                is MovieDetailsEffect.NavigateToLogin -> {}
                is MovieDetailsEffect.NavigateToCastDetails -> navigateToCastDetails(effect.personId)

                is MovieDetailsEffect.NavigateToMoviesRecommended -> navigateToMoviesRecommended(
                    effect.movieId,
                    effect.title
                )
            }
        }
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(state.isLoadingMovieDetails) {
            Progress(modifier = Modifier.size(40.dp))
        }
    }
    AnimatedVisibility(!state.isLoadingMovieDetails) {
        MovieDetailsContent(
            modifier = Modifier.fillMaxSize(),
            state = state,
            interaction = viewModel,
            onBackButtonClick = onBackButtonClick,
            onClickPlay = onClickPlay,
            onClickPoster = onClickPoster,
        )
    }

}

@Composable
private fun MovieDetailsContent(
    modifier: Modifier,
    state: MovieDetailsScreenState,
    interaction: MovieDetailsInteractionListener,
    onBackButtonClick: () -> Unit,
    onClickPlay: () -> Unit,
    onClickPoster: (Int) -> Unit,
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
                    type = TypeOfScreen.MOVIE.toString(),
                    name = state.movie.title,
                    rating = state.movie.rating,
                    imageUrl = state.movie.posterUrl,
                    genres = state.movieGenres,
                    duration = state.movie.duration,
                    releaseYear = state.movie.releaseYear,
                    onClickAdd = interaction::onAddToCollectionClick,
                    onClickPlay = onClickPlay,
                    isScrolled = isScrollingUp,
                    durationAnimation = 0,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        item {
            InfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.storyline),
                description = state.movie.description
            )
        }
        item {
            StarCastSection(
                title = stringResource(R.string.star_cast),
                onCastClick = { interaction.navigateToCastDetailsScreen(it) },
                castList = state.cast
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
            PosterListSection(
                title = stringResource(R.string.you_might_also_like),
                endText = stringResource(R.string.show_more),
                posters = state.recommendedMovies,
                onClickEndText = {
                    interaction.navigateToMovieRecommendation(state.movie.id, state.movie.title)
                },
                onClickPoster = { onClickPoster(it.id) }
            )
        }
        item {
            RatingSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClickCard = interaction::onGiveStarsClick
            )
        }
        item {

            AnimatedVisibility(visible = true) {

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (index in 0..min(2, state.movieReviews.size - 1)) {
                        val review = state.movieReviews[index]
                        val padding = when (index) {
                            0 -> Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                            1 -> Modifier.padding(horizontal = 16.dp)
                            2 -> Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
                            else -> Modifier
                        }
                        ReviewCard(
                            modifier = padding,
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
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        content = {
            AddToCollectionContent(
                title = "My Favorite TV",
                isLoading = false,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            AddToCollectionContent(
                title = "My WatchLis",
                isLoading = false,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            AddToCollectionContent(
                title = "Cristian Bale Movies",
                isLoading = false,
                modifier = Modifier.padding(vertical = 16.dp)
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
                RatingSelector()
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    text = stringResource(R.string.add_to_rate),
                    enabled = false,
                    onClick = onClickPlay
                )
            }
        }
    )
}