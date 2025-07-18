package com.giraffe.details.screens.recommended

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.recomended.RecommendedContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecommendedScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RecommendedViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadRecommendedSeries(2288, 1)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(state.isLoadingRecommended) {
            Progress(modifier = Modifier.size(40.dp))
        }
        AnimatedVisibility(!state.isLoadingRecommended) {
            RecommendedContent(
                //state = state,

                )
        }
    }
}


