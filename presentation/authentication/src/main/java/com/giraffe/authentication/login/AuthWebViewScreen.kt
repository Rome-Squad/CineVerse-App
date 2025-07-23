package com.giraffe.authentication.login

import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.Composable

import androidx.navigation.NavController
import com.google.accompanist.web.WebView
import com.google.accompanist.web.AccompanistWebViewClient

import com.google.accompanist.web.rememberWebViewState

@Composable
fun AuthWebViewScreen(onBack: () -> Unit) {
    val signUpUrl = "https://www.themoviedb.org/signup"
    var webViewState = rememberWebViewState(url=signUpUrl)
    WebView(
        state = webViewState,
        onCreated = { it.settings.javaScriptEnabled = true },
        client = object :  AccompanistWebViewClient(){
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                if (url?.contains("themoviedb.org/login") == true) {
                    onBack()
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                //  URL should be handled by WebView or if we need to navigate manually
                request?.url?.let {
                    if (it.toString().contains("themoviedb.org/login")) {
                        onBack()
                        return true
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    )
}