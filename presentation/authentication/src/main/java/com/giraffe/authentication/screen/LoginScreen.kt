package com.giraffe.authentication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.R
import com.giraffe.authentication.composable.LogoSection
import com.giraffe.authentication.composable.ScreenContent
import com.giraffe.designsystem.composable.button_type.SecondaryButton
import com.giraffe.designsystem.theme.Theme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
){
    LoginScreenContent(
        modifier = modifier
    )
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LogoSection(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
        ScreenContent(modifier = Modifier.weight(1f))
        SecondaryButton(
            text = stringResource(R.string.create_a_new_account),
            enabled = true,
            isLoading = false,
            onClick = {},
        )
        Box(modifier = Modifier.padding(top = 24.dp, bottom = 6.5.dp)) {
            Box(
                modifier = Modifier
                    .size(width = 120.dp, height = 3.dp)
                    .background(Theme.color.shade.secondary)
            )
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}