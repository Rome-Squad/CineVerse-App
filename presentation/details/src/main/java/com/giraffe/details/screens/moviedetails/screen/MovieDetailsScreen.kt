package com.giraffe.details.screens.moviedetails.screen

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
    navController: NavController,
    modifier: Modifier = Modifier,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit = {},
    viewModel: MovieDetailsViewModel = koinViewModel(parameters = { parametersOf(movieID) })
) {
    val state = viewModel.state.collectAsState().value


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MovieDetailsEffect.NavigateToMovies -> {
                    //navController.navigateToMovieDetails(movieID)
                }

                is MovieDetailsEffect.NavigateToReviews -> {
                    navigateToReviews(effect.reviews)
                }

                is MovieDetailsEffect.Error -> {}
                MovieDetailsEffect.NavigateToCollection -> {}
                MovieDetailsEffect.NavigateToLogin -> {}
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
        AnimatedVisibility(!state.isLoadingMovieDetails) {
            MovieDetailsContent(
                modifier = modifier, state = state, interaction = viewModel
            )
        }
    }
}

@Composable
private fun MovieDetailsContent(
    modifier: Modifier, state: MovieDetailsScreenState, interaction: MovieDetailsInteractionListener
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .systemBarsPadding()
            .background(Theme.color.background.screen),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        // Header
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .background(Theme.color.background.screen),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppBar(
                showBackButton = true, onBackButtonClick = {})
            MainMovieOrSeriesDetailsAnimatedContent(
                type = TypeOfScreen.MOVIE.toString(),
                name = state.movie.title,
                rating = state.movie.rating,
                image = state.movie.posterUrl,
                genres = state.movieGenres,
                releaseYear = state.movie.releaseYear,
                onClickAdd = interaction::onAddToCollectionClick,
                onClickPlay = {},
                isScrolled = scrollState.value > 0,
                durationAnimation = 2000
            )
        }

        // Scrolling Body
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .verticalScroll(scrollState)
                .background(Theme.color.background.screen),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InfoSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                title = stringResource(R.string.storyline),
                description = state.movie.description
            )

            StarCastSection(
                title = stringResource(R.string.star_cast),
                onShowMoreClick = {},
                onCastClick = {},
                castList = state.cast
            )

            StaffInfoSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.behind_the_scenes),
                staffList = state.crew
            )

            MoviesListSection(
                title = stringResource(R.string.you_might_also_like),
                endText = stringResource(R.string.show_more),
                movies = state.recommendedMovies,
                onClickEndText = {},
                onClickPoster = {})

            RatingSection(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClickCard = interaction::onGiveStarsClick
            )

            AnimatedVisibility(
                visible = state.movieReviews.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut()
            ) {
                InfoSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = stringResource(R.string.top_reviews),
                    description = state.movie.description
                )
            }

            AnimatedVisibility(state.movieReviews.isNotEmpty()) {

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
                    onClick = {})
            }
        })
}
