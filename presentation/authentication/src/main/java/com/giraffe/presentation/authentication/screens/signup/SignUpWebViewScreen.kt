package com.giraffe.presentation.authentication.screens.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.giraffe.presentation.authentication.utils.WebViewConstants.LOGIN_PATH
import com.giraffe.presentation.authentication.utils.WebViewConstants.SIGN_UP_URL
import com.giraffe.presentation.authentication.R
import com.giraffe.presentation.authentication.screens.component.CustomWebView
@Composable
fun SignUpWebViewScreen(onBack: () -> Unit) {


    CustomWebView (
    url = SIGN_UP_URL,
    title = stringResource(R.string.sign_up),
    onBack = onBack,
    onPageFinished = { url ->
        if (url?.contains(LOGIN_PATH) == true) {
            onBack()
        }
    },
    onShouldOverrideUrlLoading = { request ->
        request?.url?.let {
            if (it.toString().contains(LOGIN_PATH)) {
                onBack()
                return@CustomWebView true
            } else if (it.toString() != SIGN_UP_URL) {
                return@CustomWebView true
            }
        }
        false
    }
)
}