package com.giraffe.cineverseapp.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.R
import com.giraffe.presentation.designsystem.theme.CinVerseTheme
import com.giraffe.presentation.designsystem.theme.Theme

@Composable
fun BSRatingContent(
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    rate: Int = 0,
    onRateClick: (Int) -> Unit = {},
    onAddRatingClick: (Int) -> Unit = {}
) {

    val enabled = rate != 0

    val backgroundColor by animateColorAsState(
        targetValue = if (enabled)
            Theme.color.button.primary
        else
            Theme.color.button.disabled,
        label = "Animated Background Color"
    )

    val contentColor by animateColorAsState(
        targetValue = if (enabled)
            Theme.color.button.onPrimary
        else
            Theme.color.button.onDisabled,
        label = "Animated Background Color"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RatingSelector(
            modifier = Modifier,
            iconSize = iconSize,
            rate = rate,
            onRateClick = onRateClick
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .background(
                    color = backgroundColor
                )
                .clickable(
                    enabled = enabled
                ) {
                    onAddRatingClick(rate)
                }
                .padding(
                    vertical = 14.dp
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(R.string.add_rating),
                style = Theme.textStyle.body.medium.medium,
                color = contentColor
            )

        }

    }

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewBSRatingContent() {

    CinVerseTheme(
        isDarkTheme = false
    ) {
        BSRatingContent(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewBSRatingContentDark() {

    CinVerseTheme(
        isDarkTheme = true
    ) {
        BSRatingContent(
            modifier = Modifier
        )
    }
}
