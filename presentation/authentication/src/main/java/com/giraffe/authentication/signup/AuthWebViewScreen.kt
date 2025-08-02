package com.giraffe.authentication.signup

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.R
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun AuthWebViewScreen(onBack: () -> Unit) {
    val signUpUrl = "https://www.themoviedb.org/signup"
    var webViewState = rememberWebViewState(url = signUpUrl)
    val isLoading = webViewState.loadingState is LoadingState.Loading

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            AppBar(
                title = stringResource(R.string.sign_up),
                showBackButton = true,
                onBackButtonClick = { onBack() },
            )

            // WebView Content
            WebView(
                state = webViewState,
                onCreated = {
                    it.settings.javaScriptEnabled = true
                    it.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                },
                client = object : AccompanistWebViewClient() {
                    override fun onPageFinished(view: WebView, url: String?) {
                        super.onPageFinished(view, url)
                        if (url?.contains("themoviedb.org/login") == true) {
                            onBack()
                        }
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        request?.url?.let {
                            if (it.toString().contains("themoviedb.org/login")) {
                                onBack()
                                return true
                            } else if (it.toString() != signUpUrl) {
                                return true
                            }
                        }
                        return super.shouldOverrideUrlLoading(view, request)
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