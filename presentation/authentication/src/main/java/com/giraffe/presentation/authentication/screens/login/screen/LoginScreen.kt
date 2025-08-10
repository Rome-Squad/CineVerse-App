package com.giraffe.presentation.authentication.screens.login.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.presentation.authentication.screens.login.LoginEffect
import com.giraffe.presentation.authentication.screens.login.LoginViewModel
import com.giraffe.presentation.authentication.utils.toStringResource

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToWebViewScreen: () -> Unit,
    navigateToResetPasswordScreen: () -> Unit,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val state by viewModel.state.collectAsState()
    val effectFlow = viewModel.effect

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToWebViewScreen -> navigateToWebViewScreen()

                is LoginEffect.NavigateToHomeScreen -> navigateToHomeScreen()

                is LoginEffect.NavigateToResetPasswordScreen -> navigateToResetPasswordScreen()

                is LoginEffect.PopBack -> onBackClick()
                is LoginEffect.ShowErrorMessage -> {
                    Toast.makeText(
                        context,
                        context.getString(effect.throwable.toStringResource()),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    LoginContent(
        modifier = modifier,
        state = state,
        interaction = viewModel
    )
}
