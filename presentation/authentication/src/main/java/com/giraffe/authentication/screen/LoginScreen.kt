package com.giraffe.authentication.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.authentication.R
import com.giraffe.authentication.composable.LoginForm
import com.giraffe.authentication.composable.LogoSection
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.theme.Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToWebViewScreen: () -> Unit = {},
    navigateToResetPasswordScreen: () -> Unit = {},
    popBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val effectFlow = viewModel.effect

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToWebViewScreen -> navigateToWebViewScreen()

                is LoginEffect.NavigateToHomeScreen -> navigateToHomeScreen()

                is LoginEffect.NavigateToResetPasswordScreen -> navigateToResetPasswordScreen()

                is LoginEffect.PopBack -> popBack()

                is LoginEffect.Error -> {}
            }
        }
    }


    LoginContent(
        modifier = modifier,
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun LoginContent(
    state: LoginScreenState,
    interaction: LoginInteractionListener,
    modifier: Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LogoSection(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                )
        )

        LoginForm(
            modifier = Modifier
                .padding(bottom = 96.dp),
            state = state,
            interaction = interaction
        )

        SecondaryButton(
            modifier = Modifier.padding(bottom = 24.dp),
            text = stringResource(R.string.create_a_new_account),
            enabled = true,
            isLoading = false,
            onClick = interaction::onCreateNewAccountClick
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
                    iconTintColor = Theme.color.brand.primary,
                    buttonBackgroundColor = Theme.color.brand.primary,
                    iconBackgroundColor = Theme.color.brand.tertiary,
                    titlePrimaryButton = stringResource(R.string.go_to_website),
                    titleSecondaryButton = stringResource(R.string.cancel),
                    onClickPrimaryButton = interaction::onGoToWebsiteClick,
                    onClickSecondaryButton = interaction::onDismissCreateNewAccountBottomSheet
                )
            }
        )
    }
}