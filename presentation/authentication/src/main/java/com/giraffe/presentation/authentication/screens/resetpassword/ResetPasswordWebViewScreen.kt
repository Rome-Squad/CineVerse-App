package com.giraffe.presentation.authentication.screens.resetpassword

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.giraffe.presentation.authentication.utils.WebViewConstants.LOGIN_PATH
import com.giraffe.presentation.authentication.utils.WebViewConstants.RESET_PASSWORD_URL
import com.giraffe.presentation.authentication.R
import com.giraffe.presentation.authentication.screens.component.CustomWebView

@Composable
fun ResetPasswordWebViewScreen(onBack: () -> Unit) {
    val resetPasswordUrl = RESET_PASSWORD_URL
    val context = LocalContext.current


    CustomWebView (
        url = resetPasswordUrl,
        title = stringResource(R.string.reset_password),
        onBack = onBack,
        onPageFinished = { url ->
            if (url?.contains(LOGIN_PATH) == true) {
                Toast.makeText(
                    context,
                    context.getString(R.string.check_your_email_to_reset_password),
                    Toast.LENGTH_LONG
                ).show()
                onBack()
            }
        },
        onShouldOverrideUrlLoading = { request ->
            request?.url?.let {
                if (it.toString().contains(LOGIN_PATH)) {
                    onBack()
                    return@CustomWebView true
                } else if (it.toString() != RESET_PASSWORD_URL) {
                    return@CustomWebView true
                }
            }
            false
        }
    )
}