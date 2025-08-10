package com.giraffe.presentation.details.screens.seasons.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.screens.seasons.SeasonsViewModel

@Composable
fun SeasonsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeasonsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(state.isLoading) {
            Progress(
                modifier = Modifier
                    .size(40.dp)
            )
        }
        AnimatedVisibility(!state.isLoading) {
            SeasonsContent(
                state = state,
                onBackClick = onBackClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}