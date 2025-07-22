package com.giraffe.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.R


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    userName: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                painter = painterResource(Theme.icons.colored.logo),
                contentDescription = stringResource(R.string.image_logo),
            )
            Column {
                Text(
                    text = stringResource(R.string.welcome),
                    style = Theme.textStyle.body.sm.regular,
                    color = Theme.color.shade.secondary
                )
                Text(
                    text = userName,
                    style = Theme.textStyle.title.sm,
                    color = Theme.color.shade.primary
                )
            }
        }
    }
}