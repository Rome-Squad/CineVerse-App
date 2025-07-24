package com.giraffe.authentication.resetpassword

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun ResetPasswordWebViewScreen(onBack: () -> Unit) {
    val resetPasswordUrl = "https://www.themoviedb.org/reset-password"
    var webViewState = rememberWebViewState(url = resetPasswordUrl)
    val context = LocalContext.current

    WebView(
        state = webViewState,
        onCreated = { it.settings.javaScriptEnabled = true },
        client = object : AccompanistWebViewClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                if (url?.contains("themoviedb.org/login") == true) {
                    Toast.makeText(context, "Check Your Email To Reset Password", Toast.LENGTH_LONG)
                        .show()
                    onBack()
                }
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                //  URL should be handled by WebView or if we need to navigate manually
                request?.url?.let {
                    if (it.toString().contains("themoviedb.org/login")) {
                        onBack()
                        return true
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        })
}