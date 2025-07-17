package com.giraffe.details.screens.moviedetails.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.AddToCollectionContent
import com.giraffe.details.components.MainMovieOrSeriesDetailsAnimatedContent
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.RatingSelector
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.screens.moviedetails.MovieDetailsScreenState
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.details.utils.TypeOfDetailsScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun MovieDetailsScreen(
    movieID: Int,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(movieID) {
        viewModel.loadMovieDetails(movieID)
    }

    MovieDetailsContent(
        modifier = modifier,
        state = state,
        onShowMoreReviewsClick = viewModel::onShowMoreReviewsClick,
        onAddToCollectionClick = viewModel::onAddToCollectionClick,
        onShowMoreMoviesClick = viewModel::onShowMoreMoviesClick,
        onGiveStarClick = viewModel::onGiveStarsClick,
        onDismissAddToCollectionBottomSheet = viewModel::onDismissAddToCollectionBottomSheet,
        onDismissAddRatingBottomSheet = viewModel::onDismissGiveStarsBottomSheet,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsContent(
    modifier: Modifier,
    state: MovieDetailsScreenState,
    onShowMoreReviewsClick: () -> Unit,
    onAddToCollectionClick: () -> Unit,
    onDismissAddToCollectionBottomSheet: () -> Unit,
    onShowMoreMoviesClick: () -> Unit,
    onGiveStarClick: () -> Unit,
    onDismissAddRatingBottomSheet: () -> Unit,
) {

    LazyColumn(
        modifier = modifier
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {
        item {
            AppBar(
                showBackButton = true, modifier = Modifier.padding(16.dp)
            )
        }
        item {
            MainMovieOrSeriesDetailsAnimatedContent(
                type = TypeOfDetailsScreen.MOVIE.name,
                name = state.movie.title,
                rating = state.movie.rating,
                image = state.movie.posterUrl,
                genres = state.movieGenres,
                releaseYear = state.movie.releaseYear,
                duration = state.movie.duration.toString(),
                onClickAdd = onAddToCollectionClick,
                onClickPlay = {},
                isScrolled = false
            )
        }
        item {
            InfoSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                title = stringResource(R.string.storyline),
                description = state.movie.description
            )
        }
        item {
            state.cast.map { }
            StarCastSection(
                title = stringResource(R.string.star_cast),
                onShowMoreClick = {},
                castList = state.cast
            )
        }
        item {
            StaffInfoSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                title = stringResource(R.string.behind_the_scenes),
                staffList = state.crew
            )
        }
        item {
            MoviesListSection(
                title = stringResource(R.string.you_might_also_like),
                endText = stringResource(R.string.show_more),
                movies = state.recommendedMovies,
                onClickEndText = onShowMoreMoviesClick,
                onClickPoster = {})
        }

        item {
            RatingSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                onClickCard = onGiveStarClick
            )
        }

        item {
            AnimatedVisibility(
                visible = state.movieReviews.take(3).isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.top_reviews),
                        color = Theme.color.shade.primary,
                        style = Theme.textStyle.title.sm,
                    )

                    Text(
                        text = stringResource(R.string.show_more),
                        color = Theme.color.brand.primary,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable { onShowMoreReviewsClick() },
                        style = Theme.textStyle.body.md.medium,
                    )
                }
            }
        }

        itemsIndexed(state.movieReviews.take(3)) { index, review ->
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
                reviewerImageSource = review.authorImageUrl,
                reviewerName = review.authorName,
                reviewerUsername = review.authorUserName
            )
        }
    }

    BaseBottomSheet(
        isVisible = state.isVisibleAddToCollectionBottomSheet,
        onDismiss = onDismissAddToCollectionBottomSheet,
        title = stringResource(R.string.add_to_collection),
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
        },
    )
    BaseBottomSheet(
        isVisible = state.isVisibleGiveStarsBottomSheet,
        onDismiss = onDismissAddRatingBottomSheet,
        title = stringResource(R.string.rate_the_movie),
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
                    text = stringResource(R.string.add_to_rate),
                    enabled = false,
                    onClick = {})
            }
        }
    )
}


@Preview
@Composable
private fun MovieDetailsPreview() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        MovieDetailsScreen(
            movieID = 286,
        )
    }
}