package com.giraffe.presentation.profile.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R

enum class Language(
    @param:StringRes val label: Int,
    val code: String,
    val flag: @Composable () -> Unit
) {
    ARABIC(
        label = R.string.arabic,
        code = "ar",
        flag = {
            Image(
                painter = painterResource(
                    id = R.drawable.colored_iraq_flag
                ),
                contentDescription = "Arabic",
                modifier = Modifier.size(32.dp)
            )
        }
    ),
    ENGLISH(
        label = R.string.english,
        code = "en",
        flag = {
            Image(
                painter = painterResource(
                    id = R.drawable.colored_uk_flag
                ),
                contentDescription = "English",
                modifier = Modifier.size(32.dp)
            )
        }
    )
}