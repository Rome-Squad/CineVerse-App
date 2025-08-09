package com.giraffe.presentation.authentication.login.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.presentation.authentication.login.LoginInteractionListener
import com.giraffe.presentation.authentication.login.LoginScreenState
import com.giraffe.designsystem.composable.DefaultTextField
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    interaction: LoginInteractionListener
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        DefaultTextField(
            startIcon = painterResource(Theme.icons.outline.user),
            placeholder = stringResource(R.string.enter_your_username),
            value = state.username,
            onValueChange = interaction::onUsernameChanged,
            errorMessage = state.usernameErrorMessage?.let { stringResource(it) },
            label = stringResource(R.string.username),
            maxLines = 1,
            isPassword = false
        )

        DefaultTextField(
            startIcon = painterResource(Theme.icons.outline.user),
            placeholder = stringResource(R.string.enter_your_password),
            value = state.password,
            onValueChange = interaction::onPasswordChanged,
            errorMessage = state.passwordErrorMessage?.let { stringResource(it) },
            label = stringResource(R.string.password),
            maxLines = 1,
            isPassword = true,
            modifier = Modifier.padding(top = 16.dp)
        )

        AnimatedVisibility(
            visible = state.screenErrorMessage != null,
            enter = slideInVertically(tween(250, easing = LinearEasing)) { -it },
            exit = slideOutVertically(tween(0)) { it }
        ) {
            Text(
                text = state.screenErrorMessage?.let { stringResource(it) } .orEmpty(),
                style = Theme.textStyle.body.sm.regular,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                letterSpacing = 0.sp,
                color = Theme.color.additional.primary.red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Text(
            text = stringResource(com.giraffe.designsystem.R.string.forgot_password),
            style = Theme.textStyle.body.md.regular,
            color = Theme.color.shade.secondary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .noHoverClickable(onClick = interaction::onForgotPasswordClick)
                .padding(top = 12.dp)
        )

        PrimaryButton(
            text = stringResource(R.string.login),
            enabled = state.screenErrorMessage == null
                    && state.passwordErrorMessage == null
                    && state.usernameErrorMessage == null
                    && state.username.isNotBlank()
                    && state.password.isNotBlank(),
            isLoading = state.isLoadingLogin,
            onClick = {
                focusManager.clearFocus()
                interaction.onLoginClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )

        SecondaryButton(
            text = stringResource(R.string.join_as_guest),
            enabled = true,
            isLoading = false,
            onClick = interaction::onJoinAsGuestClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}