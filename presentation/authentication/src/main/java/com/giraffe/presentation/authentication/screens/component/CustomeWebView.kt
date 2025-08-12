package com.giraffe.presentation.authentication.screens.component

import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun CustomWebView(
    url: String,
    title: String,
    onBack: () -> Unit,
    onPageFinished: (String?) -> Unit,
    onShouldOverrideUrlLoading: (WebResourceRequest?) -> Boolean
) {
    val webViewState = rememberWebViewState(url = url)
    val isLoading = webViewState.loadingState is LoadingState.Loading
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            AppBar(
                title = title,
                showBackButton = true,
                onBackButtonClick = { onBack() }
            )

            WebView(
                state = webViewState,
                onCreated = {
                    it.settings.javaScriptEnabled = true
                    it.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                },
                client = object : AccompanistWebViewClient() {
                    override fun onPageFinished(view: WebView, url: String?) {
                        super.onPageFinished(view, url)
                        onPageFinished(url)
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return onShouldOverrideUrlLoading(request)
                    }
                }
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.background.screen),
                contentAlignment = Alignment.Center
            ) {
                Progress(modifier = Modifier.size(40.dp))
            }
        }
    }
}