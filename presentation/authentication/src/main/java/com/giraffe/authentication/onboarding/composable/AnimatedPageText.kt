package com.giraffe.authentication.onboarding.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.onboarding.screen.OnBoardingPage
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme

@Composable
fun AnimatedPageText(page: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(71.dp)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = page.title,
            modifier = Modifier.padding(bottom = 8.dp),
            style = Theme.textStyle.title.md,
            color = Theme.color.shade.primary,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            text = page.subtitle,
            style = Theme.textStyle.body.md.medium,
            color = Theme.color.shade.secondary,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}
