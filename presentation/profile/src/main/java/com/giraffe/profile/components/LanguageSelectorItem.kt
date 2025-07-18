package com.giraffe.profile.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.utils.Language

@Composable
fun LanguageSelectorItem(
    modifier: Modifier = Modifier,
    language: Language,
    isSelected: Boolean,
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.color.brand.primary
        else
            Theme.color.shade.primary
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.color.brand.secondary
        else
            Theme.color.shade.quaternary
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.color.brand.tertiary
        else
            Theme.color.shade.quaternary
    )

    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(
                    size = Theme.radius.lg
                )
            )
            .clip(
                shape = RoundedCornerShape(
                    size = Theme.radius.lg
                )
            )
            .background(
                color = backgroundColor
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        language.flag()

        Text(
            text = stringResource(
                id = language.label
            ),
            style = Theme.textStyle.body.md.medium,
            color = textColor
        )
    }

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewLanguageSelectorItem() {

    CineVerseTheme(
        isDarkTheme = false
    ) {

        LanguageSelectorItem(
            modifier = Modifier
                .size(
                    width = 146.dp,
                    height = 87.dp
                ),
            language = Language.ARABIC,
            isSelected = true
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewLanguageSelectorItemDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        LanguageSelectorItem(
            modifier = Modifier
                .size(
                    width = 146.dp,
                    height = 87.dp
                ),
            language = Language.ENGLISH,
            isSelected = true
        )
    }
}
