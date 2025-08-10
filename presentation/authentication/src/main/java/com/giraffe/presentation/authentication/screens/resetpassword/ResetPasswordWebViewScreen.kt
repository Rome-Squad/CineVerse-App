package com.giraffe.presentation.authentication.screens.resetpassword

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.authentication.utils.WebViewConstants.LOGIN_PATH
import com.giraffe.presentation.authentication.utils.WebViewConstants.RESET_PASSWORD_URL
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun ResetPasswordWebViewScreen(onBack: () -> Unit) {
    val resetPasswordUrl = RESET_PASSWORD_URL
    var webViewState = rememberWebViewState(url = resetPasswordUrl)
    val isLoading = webViewState.loadingState is LoadingState.Loading
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {

        Column(modifier = Modifier.fillMaxSize())
        {
            AppBar(
                title = stringResource(R.string.reset_password),
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
                        if (url?.contains(LOGIN_PATH) == true) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.check_your_email_to_reset_password),
                                Toast.LENGTH_LONG
                            ).show()
                            onBack()
                        }
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        request?.url?.let {
                            if (it.toString().contains(LOGIN_PATH)) {
                                onBack()
                                return true
                            } else if (it.toString() != RESET_PASSWORD_URL) {
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