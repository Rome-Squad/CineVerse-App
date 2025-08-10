package com.giraffe.presentation.profile.screens.settings.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.utils.Language

@Composable
fun LanguageSelector(
    modifier: Modifier = Modifier,
    currentLanguage: Language,
    onLanguageChange: (Language) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LanguageItem(
            modifier = Modifier
                .size(
                    width = 146.dp,
                    height = 87.dp
                )
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .clickable {
                    onLanguageChange(Language.ENGLISH)
                },
            language = Language.ENGLISH,
            isSelected = currentLanguage == Language.ENGLISH,
        )
        LanguageItem(
            modifier = Modifier
                .size(
                    width = 146.dp,
                    height = 87.dp
                )
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .clickable {
                    onLanguageChange(Language.ARABIC)
                },
            language = Language.ARABIC,
            isSelected = currentLanguage == Language.ARABIC
        )
    }
}

@Composable
private fun LanguageItem(
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