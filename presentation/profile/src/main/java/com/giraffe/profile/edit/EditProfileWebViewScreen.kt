package com.giraffe.profile.edit

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.R
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

private const val EDIT_PROFILE_URL = "https://www.themoviedb.org/settings/profile"

@Composable
fun EditProfileWebViewScreen(onBack: () -> Unit) {
    val webViewState = rememberWebViewState(url = EDIT_PROFILE_URL)
    val isLoading = webViewState.loadingState is LoadingState.Loading

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppBar(
                title = stringResource(R.string.edit_profile_title),
                showBackButton = true,
                onBackButtonClick = onBack,
            )

            WebView(
                state = webViewState,
                onCreated = {
                    it.settings.javaScriptEnabled = true
                    it.setBackgroundColor(Color.TRANSPARENT)
                }
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Progress(modifier = Modifier.size(40.dp))
            }
        }
    }
}