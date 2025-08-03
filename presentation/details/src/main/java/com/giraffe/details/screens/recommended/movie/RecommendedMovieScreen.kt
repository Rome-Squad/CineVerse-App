package com.giraffe.details.screens.recommended.movie

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.custom.Button
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.utils.EventListener

@Composable
fun RecommendedMoviesScreen(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: RecommendedMoviesViewModel = hiltViewModel()

    val lazyPagingItems = viewModel.recommendationScreenState.collectAsLazyPagingItems()
    EventListener(
        events = viewModel.effect,
    ) { effect ->
        when (effect) {
            is RecommendedEffectMovie.NavigateToMovieDetails -> {
                val movieId = effect.MovieId
                if (movieId != null) {
                    navigateToMovieDetails(movieId)
                } else {
                    Log.e("RecommendedMovies", "Invalid movie ID received: $movieId")
                }
            }
        }
    }


    val isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading
    val refreshError = lazyPagingItems.loadState.refresh as? LoadState.Error

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
        Column {
            RecommendedContent(
                title = viewModel.title,
                lazyPagingItems = lazyPagingItems,
                interaction = viewModel,
                onBackClick = onBackClick
            )

        }

        AnimatedVisibility(
            visible = isRefreshing,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Progress(modifier = Modifier.size(48.dp))
        }

        refreshError?.let {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                ErrorContent(
                    message = "Failed to load recommendations",
                    onRetry = { lazyPagingItems.retry() }
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = message,
            style = Theme.textStyle.body.md.semiBold,
            color = Theme.color.shade.primary,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onRetry,
            containerColor = Theme.color.brand.primary,
            contentColor = Theme.color.button.onPrimary,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            )
        ) {
            Text(
                text = stringResource(R.string.try_again),
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.button.onPrimary
            )
        }
    }
}
