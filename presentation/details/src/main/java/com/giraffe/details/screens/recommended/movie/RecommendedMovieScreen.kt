package com.giraffe.details.screens.recommended.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.screens.recommended.movie.RecommendedContent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RecommendedMoviesScreen(
    movieId: Int,
    title: String = " ",
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RecommendedMoviesViewModel = koinViewModel(
        parameters = { parametersOf(title, movieId) }
    )
) {
    val lazyPagingItems = viewModel.recommendationScreenState.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        Column {
            RecommendedContent(
                title = viewModel.title,
                lazyPagingItems = lazyPagingItems,
                navController = navController,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        AnimatedVisibility(
            visible = lazyPagingItems.loadState.refresh is LoadState.Loading,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Progress(modifier = Modifier.size(48.dp))
        }
    }
}
