package com.giraffe.match.composable


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress

@Composable
fun BaseScreenWithStates(
    isLoading: Boolean,
    isNoInternet: Boolean,
    onRetryClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    when {
        isLoading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Progress()
        }

        isNoInternet -> NoInternetScreen(onRetryClick = onRetryClick)
        else -> content()
    }
}