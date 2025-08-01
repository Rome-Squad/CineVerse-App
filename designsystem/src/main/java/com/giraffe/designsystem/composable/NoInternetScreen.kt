package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.Theme
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.composable.custom.Icon

@Composable
fun NoInternetScreen(modifier: Modifier=Modifier,onRetryClick: () -> Unit={}) {
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
                contentDescription = "No Internet Icon",
                modifier = Modifier.size(28.dp),
                tint = Theme.color.additional.primary.red
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Main Error Text
        Text(
            text = "Oops! No Internet",
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtext
        Text(
            text = "Looks like you're offline. Let's reconnect so you don't miss out!",
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.secondary,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Retry Button
        PrimaryButton (
            text = "Try Again",
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
    NoInternetScreen(onRetryClick = { /* handle retry logic */ })
}