package com.giraffe.presentation.profile.screens.edit

import android.graphics.Color
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.giraffe.presentation.profile.utils.FilePicker
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

private const val EDIT_PROFILE_URL = "https://www.themoviedb.org/settings/profile"
private const val LOGIN_URL = "https://www.themoviedb.org/login"

@Composable
fun EditProfileWebViewScreen(onBack: () -> Unit) {
    val webViewState = rememberWebViewState(url = EDIT_PROFILE_URL)
    val isLoading = webViewState.loadingState is LoadingState.Loading
    val webViewClient = remember { RestrictedWebViewClient() }
    val chromeClient = remember { FileChooserWebChromeClient() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .navigationBarsPadding()
            .imePadding()
    ) {
        AppBar(
            title = stringResource(R.string.edit_profile_title),
            showBackButton = true,
            onBackButtonClick = onBack,
            modifier = Modifier.statusBarsPadding()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            WebView(
                state = webViewState,
                client = webViewClient,
                chromeClient = chromeClient,
                modifier = Modifier.fillMaxSize(),
                onCreated = {
                    it.settings.javaScriptEnabled = true
                    it.setBackgroundColor(Color.TRANSPARENT)
                }
            )

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
}

private class RestrictedWebViewClient : AccompanistWebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val clickedUrl = request?.url?.toString() ?: return false
        val isAllowed = clickedUrl.startsWith(EDIT_PROFILE_URL) || clickedUrl.startsWith(LOGIN_URL)
        return !isAllowed
    }
}

private class FileChooserWebChromeClient : AccompanistWebChromeClient() {
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        FilePicker.filePathCallback = filePathCallback
        FilePicker.openFileChooser?.invoke()
        return true
    }
}