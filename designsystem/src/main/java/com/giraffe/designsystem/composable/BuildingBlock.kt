package com.giraffe.designsystem.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun BuildingBlock(
    icons: List<Int>,
    label: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {

    val color = if (isSelected) Theme.color.brand.primary else Theme.color.shade.tertiary
    val icon = if (isSelected) icons[0] else icons[1]

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.icon_in_navigation_bottom),
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = Theme.textStyle.label.md.regular,
            color = color,
            maxLines = 1,
        )
    }
}

@PreviewLightDark
@Composable
fun BuildingBlockPreview() {
    var isSelected by remember { mutableStateOf(true) }
    CineVerseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BuildingBlock(
                icons = listOf(Theme.icons.dueTone.userSquare, Theme.icons.outline.userSquare),
                label = "Label",
                isSelected = isSelected,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = {
                            isSelected = !isSelected
                        }
                    )
            )
        }
    }
}