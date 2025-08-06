package com.giraffe.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.utils.Language

@Composable
fun LanguageSelector(
    modifier: Modifier = Modifier,
    currentLanguage: Language,
    onLanguageChange: (Language) -> Unit
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LanguageSelectorItem(
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

        LanguageSelectorItem(
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
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewLanguageSelector() {

    var currentLanguage by rememberSaveable {
        mutableStateOf(Language.ENGLISH)
    }

    CineVerseTheme(
        isDarkTheme = false
    ) {
        LanguageSelector(
            modifier = Modifier
                .padding(
                    16.dp
                ),
            currentLanguage = currentLanguage,
            onLanguageChange = {
                currentLanguage = it
            }
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewLanguageSelectorDark() {

    var currentLanguage by rememberSaveable {
        mutableStateOf(Language.ENGLISH)
    }

    CineVerseTheme(
        isDarkTheme = true
    ) {
        LanguageSelector(
            modifier = Modifier
                .padding(
                    16.dp
                ),
            currentLanguage = currentLanguage,
            onLanguageChange = {
                currentLanguage = it
            }
        )
    }
}
