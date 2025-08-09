package com.giraffe.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.R


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    userName: String,
    isLoggedIn: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        if (isLoggedIn) {
            AppBar(
                modifier = Modifier.padding(start = 16.dp),
                image = painterResource(Theme.icons.colored.logo),
                caption = stringResource(R.string.welcome),
                title = userName
            )
        } else {
            AppBar(
                modifier = Modifier.padding(start = 16.dp),
                image = painterResource(Theme.icons.colored.logo),
                title = stringResource(com.giraffe.designsystem.R.string.home)
            )
        }
    }
}