package com.giraffe.authentication.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.R
import com.giraffe.authentication.screen.LoginInteractionListener
import com.giraffe.authentication.screen.LoginState
import com.giraffe.designsystem.composable.DefaultTextField
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.theme.Theme

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    state: LoginState,
    interaction: LoginInteractionListener
){
    val context = LocalContext.current

    Column (
        modifier = modifier.padding(horizontal = 16.dp)
    ){
        DefaultTextField(
            startIcon = painterResource(Theme.icons.outline.user),
            placeholder = stringResource(R.string.enter_your_username),
            value = state.username,
            onValueChange = interaction::onUsernameChanged,
            errorMessage = state.usernameErrorMessage?.let { context.getString(it) } ,
            label = stringResource(R.string.username),
            maxLines = 1,
            isPassword = false,
        )

        DefaultTextField(
            modifier = Modifier.padding(top = 16.dp),
            startIcon = painterResource(Theme.icons.outline.user),
            placeholder = stringResource(R.string.enter_your_password),
            value = state.password,
            onValueChange = interaction::onPasswordChanged,
            errorMessage = state.passwordErrorMessage?.let { context.getString(it) },
            label = stringResource(R.string.password),
            maxLines = 1,
            isPassword = true,
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            text = stringResource(R.string.login),
            enabled = true,
            isLoading = state.isLoading,
            onClick = interaction::onLoginClick,
        )

        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.join_as_guest),
            enabled = true,
            isLoading = false,
            onClick = interaction::onJoinAsGuestClick,
        )
    }
}