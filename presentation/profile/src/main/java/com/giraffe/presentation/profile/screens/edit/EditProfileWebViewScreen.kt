package com.giraffe.presentation.profile.screens.edit

import android.graphics.Color
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

private const val EDIT_PROFILE_URL = "https://www.themoviedb.org/settings/profile"

@Composable
fun EditProfileWebViewScreen(onBack: () -> Unit) {
    val webViewState = rememberWebViewState(url = EDIT_PROFILE_URL)
    val isLoading = webViewState.loadingState is LoadingState.Loading
    val webViewClient = remember { RestrictedWebViewClient() }

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
                client = webViewClient,
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

private class RestrictedWebViewClient : AccompanistWebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val clickedUrl = request?.url?.toString() ?: return false
        return !clickedUrl.startsWith(EDIT_PROFILE_URL)
    }
}