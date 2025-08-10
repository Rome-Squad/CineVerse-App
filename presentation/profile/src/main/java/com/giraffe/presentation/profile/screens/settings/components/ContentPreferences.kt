package com.giraffe.presentation.profile.screens.settings.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.user.entity.ContentPreference

@Composable
fun ContentPreferences(
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

@Composable
private fun ContentPreferenceItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    icon: Painter,
    title: String,
    caption: String,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.tertiary else Theme.color.background.bottomSheetCard,
        label = "BackgroundColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.primary else Color.Transparent,
        label = "BorderColor"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .background(color = backgroundColor)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(Theme.radius.md),
                    color = if (isSelected) Theme.color.brand.secondary else Theme.color.shade.quaternary
                )
                .padding(8.dp),
            painter = icon,
            contentDescription = title,
            tint = if (isSelected) Theme.color.brand.primary else Theme.color.shade.primary
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.body.md.medium,
                color = if (isSelected) Theme.color.brand.primary else Theme.color.shade.primary
            )
            Text(
                text = caption,
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.shade.secondary
            )
        }
    }
}