package com.giraffe.authentication.login.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.login.LoginInteractionListener
import com.giraffe.authentication.login.LoginScreenState
import com.giraffe.authentication.login.composable.LoginForm
import com.giraffe.authentication.login.composable.LogoSection
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R

@Composable
fun LoginContent(
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )

        LoginForm(state = state, interaction = interaction)

        Spacer(Modifier.weight(1f))

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