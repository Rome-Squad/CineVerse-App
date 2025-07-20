package com.giraffe.authentication.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.R
import com.giraffe.designsystem.composable.DefaultTextField
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.theme.Theme

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ){
        DefaultTextField(
            modifier = Modifier.padding(horizontal = 16.dp),
            startIcon = painterResource(Theme.icons.outline.user),
            placeholder = stringResource(R.string.enter_your_email_or_username),
            value = "",
            onValueChange = {},
            label = stringResource(R.string.email_or_username),
            maxLines = 1,
            isPassword = false,
        )
        DefaultTextField(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            startIcon = painterResource(Theme.icons.outline.user),
            placeholder = stringResource(R.string.enter_your_password),
            value = "",
            onValueChange = {},
            label = stringResource(R.string.password),
            maxLines = 1,
            isPassword = true,
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            text = stringResource(R.string.login),
            enabled = true,
            isLoading = false,
            onClick = {},
        )
        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.join_as_guest),
            enabled = true,
            isLoading = false,
            onClick = {},
        )
    }
}