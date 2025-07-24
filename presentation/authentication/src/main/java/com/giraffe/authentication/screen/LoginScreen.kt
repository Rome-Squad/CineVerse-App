package com.giraffe.authentication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.R
import com.giraffe.authentication.composable.LoginBody
import com.giraffe.authentication.composable.LogoSection
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.theme.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    navigateToHomeScreen: () -> Unit = {},
    navigateToWebViewScreen: () -> Unit = {},
    navigateToResetPasswordScreen: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val effectFlow = viewModel.effect

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToWebViewScreen -> {
                    navigateToWebViewScreen()
                }

                is LoginEffect.NavigateToHomeScreen -> {
                    navigateToHomeScreen()
                }

                is LoginEffect.NavigateToResetPasswordScreen -> {
                    navigateToResetPasswordScreen()
                }

                is LoginEffect.Error -> {}
                else -> {}
            }
        }
    }


    LoginContent(
        modifier = modifier, state = state, interaction = viewModel
    )
}

@Composable
private fun LoginContent(
    modifier: Modifier, state: LoginState, interaction: LoginInteractionListener
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LogoSection(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
        LoginBody(
            modifier = Modifier.weight(1f), state = state, interaction = interaction
        )
        SecondaryButton(
            text = stringResource(R.string.create_a_new_account),
            enabled = true,
            isLoading = false,
            onClick = interaction::onCreateNewAccountClick
        )

        Box(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 6.5.dp)
                .size(width = 120.dp, height = 3.dp)
                .background(Theme.color.shade.secondary)
        )

        BaseBottomSheet(
            isVisible = state.isVisibleCreateNewAccountBottomSheet,
            onDismiss = interaction::onDismissCreateNewAccountBottomSheet,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 28.dp),
            content = {
                MessageInfoBox(
                    title = stringResource(R.string.join_cineverse),
                    caption = stringResource(R.string.caption_for_sign_up),
                    icon = painterResource(Theme.icons.dueTone.linkMinimalistic),
                    iconTint = Theme.color.brand.primary,
                    iconBackgroundColor = Theme.color.brand.tertiary,
                    titlePrimaryButton = stringResource(R.string.go_to_website),
                    titleSecondaryButton = stringResource(R.string.cancel),
                    onClickPrimaryButton = interaction::onGoToWebsiteClick,
                    onClickSecondaryButton = interaction::onDismissCreateNewAccountBottomSheet
                )
            })
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}