package com.giraffe.authentication.login

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun AuthWebViewScreen(navController: NavController) {
    val signUpUrl = "https://www.themoviedb.org/signup"
    var currentUrl = remember { signUpUrl }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                loadUrl(currentUrl)
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        url?.let {
                            currentUrl = it
                            if (it.contains("themoviedb.org/login")) {
                                // Go back to previous screen if login page is reached
                                navController.popBackStack()
                            }
                        }
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        //  URL should be handled by WebView or if we need to navigate manually
                        request?.url?.let {
                            if (it.toString().contains("themoviedb.org/login")) {
                                navController.popBackStack()
                                return true
                            }
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
            }
        },
    )
}