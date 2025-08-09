package com.giraffe.presentation.details.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme

@Composable
fun RatingEmojiState(
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    rate: Int = 0
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 5) {

            val isSelected = i + 1 == rate

            val padding by animateDpAsState(
                targetValue = if (isSelected)
                    0.dp
                else
                    iconSize * .166f,
                label = stringResource(R.string.rating_emoji_animation)
            )

            Image(
                painter = painterResource(
                    id = ratingToEmoji(i + 1)
                ),
                contentDescription = stringResource(R.string.rate, rate),
                modifier = Modifier
                    .size(
                        iconSize
                    )
                    .padding(
                        all = padding
                    )
                    .alpha(
                        alpha = if (isSelected)
                            1f
                        else
                            .3f
                    )

            )

        }

    }

}

private fun ratingToEmoji(
    rate: Int
): Int = when (rate) {
    1 -> R.drawable.colored_frowning_face
    2 -> R.drawable.colored_neutral_face
    3 -> R.drawable.colored_confused_face
    4 -> R.drawable.colored_star_struck
    5 -> R.drawable.colored_smiling_face_with_smiling_eyes
    else -> 0
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewRatingEmojiState() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        RatingEmojiState(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewRatingEmojiStateDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        RatingEmojiState(
            modifier = Modifier
        )
    }
}
