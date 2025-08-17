package com.giraffe.match.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.match.R

@Composable
fun MatchScreen(
    modifier: Modifier = Modifier,
    onStartMatchingClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .statusBarsPadding()
    ) {
        AppBar(
            modifier = Modifier.padding(start = 16.dp),
            title = stringResource(id = R.string.discover_your_match),
            showBackButton = false
        )

        MatchContent(
            modifier = Modifier.fillMaxSize(),
            onStartMatchingClick = onStartMatchingClick
        )
    }
}

@Composable
fun MatchContent(
    modifier: Modifier = Modifier,
    onStartMatchingClick: () -> Unit = {}
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        MessageInfoBox(
            title = stringResource(id = R.string.not_sure_what_to_watch),
            caption = stringResource(id = R.string.match_caption),
            icon = painterResource(id = Theme.icons.dueTone.magicStick),
            iconTintColor = Theme.color.brand.primary,
            buttonBackgroundColor = Theme.color.brand.primary,
            iconBackgroundColor = Theme.color.brand.tertiary,
            isSecondaryButtonVisible = false,
            titlePrimaryButton = stringResource(id = R.string.start_matching),
            modifier = Modifier.padding(horizontal = 60.dp),
            onClickPrimaryButton = onStartMatchingClick
        )
    }
}