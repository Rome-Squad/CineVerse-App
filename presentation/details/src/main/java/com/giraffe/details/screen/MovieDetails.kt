package com.giraffe.details.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.CollectionItem
import com.giraffe.details.components.MainMovieOrSeriesDetails
import com.giraffe.details.components.RatingSection
import com.giraffe.details.components.RatingSelector
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.components.StaffInfoSection
import com.giraffe.details.components.StarCastSection
import com.giraffe.details.screens.moviedetails.MovieDetailsScreenState
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.details.utils.imageSourceToPainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetails(
    movieID: Int, modifier: Modifier = Modifier, viewModel: MovieDetailsViewModel = koinViewModel()
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
    onDismissAddRatingBottomSheet: () -> Unit
) {

    LazyColumn(
        modifier = modifier
            .background(Theme.color.background.screen)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AppBar(
                showBackButton = true, modifier = Modifier.padding(16.dp)
            )
        }
        item {
            MainMovieOrSeriesDetails(
                type = "Movie",
                poster = state.movie.posterUrl?.imageSourceToPainter()
                    ?: painterResource(R.drawable.main_poster_test),
                name = state.movie.title,
                genres = state.movieGenres,
                rating = state.movie.rating,
                duration = state.movie.duration.toString(),
                releaseDate = state.movie.releaseYear,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                onAddToCollectionClick = onAddToCollectionClick
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
                onArrowButtonClick = onGiveStarClick
            ) // when click to arrow show bottom sheet or all card ?
        }

        item {
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

        itemsIndexed(state.movieReviews.take(3)) { index, review ->
            val padding = when (index) {
                0 -> Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                1 -> Modifier.padding(horizontal = 16.dp)
                2 -> Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
                else -> Modifier
            }
            ReviewCard(
                modifier = padding,
                rate = review.author.rating.toInt(),
                reviewText = review.content,
                reviewDate = review.createdAt,
                reviewerImageSource = review.author.avatarImage,
                reviewerName = review.author.name,
                reviewerUsername = review.author.username
            )
        }

        item {
            Spacer(Modifier.height(30.dp))
        }
    }

    BaseBottomSheet(
        isVisible = state.isVisibleAddToCollectionBottomSheet,
        onDismiss = onDismissAddToCollectionBottomSheet,
        title = "Add to Collection",
        content = {
            CollectionItem(
                text = "My Favorite TV", icon = R.drawable.due_tone_folder
            )
            CollectionItem(
                text = "My WatchList", icon = R.drawable.due_tone_folder
            )
            CollectionItem(
                text = "Cristian Bale Movies", icon = R.drawable.due_tone_folder
            )
        },
    )
    BaseBottomSheet(
        isVisible = state.isVisibleGiveStarsBottomSheet,
        onDismiss = onDismissAddRatingBottomSheet,
        title = "Rate the Movie",
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RatingSelector()
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .weight(1f), text = "Add To Rate", enabled = false, onClick = {})
            }
        },
    )
}


@Preview
@Composable
private fun MovieDetailsPreview() {
    CineVerseTheme(
        isDarkTheme = false
    ) {
        MovieDetails(
            movieID = 286,
        )
    }
}