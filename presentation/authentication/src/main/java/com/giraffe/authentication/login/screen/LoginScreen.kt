package com.giraffe.authentication.login.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.authentication.login.LoginEffect
import com.giraffe.authentication.login.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToWebViewScreen: () -> Unit,
    navigateToResetPasswordScreen: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val effectFlow = viewModel.effect

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToWebViewScreen -> navigateToWebViewScreen()

                is LoginEffect.NavigateToHomeScreen -> navigateToHomeScreen()

                is LoginEffect.NavigateToResetPasswordScreen -> navigateToResetPasswordScreen()

                is LoginEffect.PopBack -> onBackClick()

            }
        }
    }

    LoginContent(
        modifier = modifier,
        state = state,
        interaction = viewModel
    )
}
