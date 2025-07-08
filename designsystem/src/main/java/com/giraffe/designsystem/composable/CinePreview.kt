package com.giraffe.designsystem.composable

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "ar - light",
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "ar - dark",
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "en - light",
    locale = "en",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "en - dark",
    locale = "en",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class CinePreview