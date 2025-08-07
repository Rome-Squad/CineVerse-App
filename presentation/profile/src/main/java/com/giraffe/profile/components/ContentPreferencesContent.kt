package com.giraffe.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.R
import com.giraffe.user.entity.ContentPreference

@Composable
fun ContentPreferencesContent(
    modifier: Modifier = Modifier,
    currentPreference: ContentPreference,
    onPreferenceSelected: (ContentPreference) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = stringResource(id = R.string.content_preferences_subtitle),
            style = Theme.textStyle.body.sm.regular,
            color = Theme.color.shade.secondary
        )

        ContentPreferenceItem(
            isSelected = currentPreference == ContentPreference.HIDE_EXPLICIT,
            icon = painterResource(id = Theme.icons.dueTone.eyeSlash),
            title = stringResource(R.string.hide_explicit_content),
            caption = stringResource(R.string.hides_revealing_or_inappropriate_posters),
            onClick = {
                onPreferenceSelected(ContentPreference.HIDE_EXPLICIT)
            }
        )

        ContentPreferenceItem(
            isSelected = currentPreference == ContentPreference.STRICT,
            icon = painterResource(id = Theme.icons.dueTone.slash),
            title = stringResource(R.string.strict_filtering),
            caption = stringResource(R.string.hides_all_content_that_includes_immodest_clothing),
            onClick = {
                onPreferenceSelected(ContentPreference.STRICT)
            }
        )

        ContentPreferenceItem(
            isSelected = currentPreference == ContentPreference.SHOW_ALL,
            icon = painterResource(id = Theme.icons.dueTone.eye),
            title = stringResource(R.string.show_all_content),
            caption = stringResource(R.string.no_filtering_all_images_will_be_displayed),
            onClick = {
                onPreferenceSelected(ContentPreference.SHOW_ALL)
            }
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun ContentPreferencesContentPreviewLight() {
    CineVerseTheme(isDarkTheme = false) {
        ContentPreferencesContent(
            onPreferenceSelected = {},
            currentPreference = ContentPreference.HIDE_EXPLICIT,
        )
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun ContentPreferencesContentPreviewDark() {
    CineVerseTheme(isDarkTheme = true) {
        ContentPreferencesContent(
            onPreferenceSelected = {},
            currentPreference = ContentPreference.HIDE_EXPLICIT,
        )
    }
}