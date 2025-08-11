package com.giraffe.presentation.profile.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Switch
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.icon.CineVerseIcons
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.Theme

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    hasSwitch: Boolean,
    onSwitchChange: (Boolean) -> Unit,
    isSwitchOn: Boolean = true,
    hasButton: Boolean,
    onRowItemClick: () -> Unit,
    isDanger: Boolean = false,
    hasBottomDivider: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (hasButton) {
                    Modifier.noHoverClickable { onRowItemClick() }
                } else {
                    Modifier
                }
            )
            .background(Theme.color.background.card)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Start
            ),
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = if (isDanger) Theme.color.additional.primary.red else Theme.color.brand.primary,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                text = title,
                modifier = Modifier,
                color = if (isDanger) Theme.color.additional.primary.red else Theme.color.shade.primary,
                style = Theme.textStyle.body.md.medium,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.End
            ),
            modifier = Modifier
        ) {
            if (hasSwitch) {
                Switch(
                    isOn = isSwitchOn,
                    onCheckedChange = onSwitchChange
                )
            }
            if (hasButton) {
                Icon(
                    painter = painterResource(id = CineVerseIcons().outline.altArrowRight),
                    contentDescription = "Navigate to $title",
                    tint = if (isDanger) Theme.color.additional.primary.red else Theme.color.shade.tertiary,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
    if (hasBottomDivider) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(1.dp)
                .background(Theme.color.shade.quaternary)
        )
    }
}