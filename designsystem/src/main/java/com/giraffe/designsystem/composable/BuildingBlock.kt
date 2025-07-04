package com.giraffe.designsystem.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun BuildingBlock(
    icon: Painter,
    label: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {

    val color by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.primary else Theme.color.shade.tertiary,
        label = "BuildingBlock"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
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
    var isSelected by remember { mutableStateOf(false) }
    CineVerseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BuildingBlock(
                icon = painterResource(Theme.icons.outline.home),
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