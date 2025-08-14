package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme

@Composable
fun NoInternetScreen(modifier: Modifier = Modifier, onRetryClick: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .size(64.dp)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(Theme.color.additional.secondary.red),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Theme.icons.dueTone.station),
                contentDescription = stringResource(R.string.no_internet_icon),
                modifier = Modifier.size(28.dp),
                tint = Theme.color.additional.primary.red
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.oops_no_internet),
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.looks_like_you_re_offline_let_s_reconnect_so_you_don_t_miss_out),
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.secondary,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = stringResource(R.string.try_again),
            enabled = true,
            buttonColorEnabled = Theme.color.brand.primary,
            modifier = Modifier
                .width(240.dp)
                .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            onClick = onRetryClick
        )
    }
}

@Preview
@Composable
fun NoInternetScreenPreview() {
    NoInternetScreen(onRetryClick = {})
}