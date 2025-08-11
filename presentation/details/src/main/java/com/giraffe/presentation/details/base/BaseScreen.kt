package com.giraffe.presentation.details.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme

@Composable
fun BaseScreen(
    title: String,
    isLoading: Boolean,
    isNoInternet: Boolean,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
    ) {
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            title = title,
            showBackButton = true,
            onBackButtonClick = onBackClick
        )
        ScreenStates(
            isLoading = isLoading,
            isNoInternet = isNoInternet,
            onRetryClick = onRetryClick

        ) {
            content()
        }
    }
}


@Composable
private fun ScreenStates(
    isLoading: Boolean,
    isNoInternet: Boolean,
    onRetryClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    when {
        isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Progress()
        }

        isNoInternet -> NoInternetScreen(onRetryClick = onRetryClick)
        else -> content()
    }
}