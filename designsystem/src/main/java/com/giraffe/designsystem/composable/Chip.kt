package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun Chip(
    text: String,
    isSelected: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                if (isSelected)
                    Theme.color.brand.tertiary
                else
                    Theme.color.background.card,
                shape = RoundedCornerShape(Theme.radius.full)
            )
            .then(
                if (isSelected) Modifier.border(
                    1.dp, Theme.color.brand.secondary, RoundedCornerShape(
                        Theme.radius.full
                    )
                )
                else Modifier
            )
            .height(32.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .clickable(
                enabled = true,
                onClick = {
                    onCheckedChange?.invoke(!isSelected)
                }
            )
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(
            modifier = Modifier
                .size(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(9.33.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Theme.color.brand.primary else Theme.color.shade.primary
                    )
                    .align(Alignment.Center)
            )
        }

        Text(
            text = text,
            color = if (isSelected) Theme.color.brand.primary else Theme.color.shade.primary,
            style = Theme.textStyle.label.md.medium,
            textAlign = TextAlign.Center,
        )

        Box(
            modifier = Modifier
                .size(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(9.33.dp)
                    .clip(CircleShape)
                    .background(Theme.color.shade.primary)
                    .align(Alignment.Center)
            )
        }

    }
}

@Preview(showSystemUi = true, backgroundColor = 0xFF121321, showBackground = true)
@Composable
private fun ChipPreview() {
    var selectedChip by remember { mutableStateOf(true) }
    CineVerseTheme(isDarkTheme = true) {
        Chip(
            modifier = Modifier.padding(64.dp),
            text = "Alaa",
            isSelected = selectedChip,
            onCheckedChange = { selectedChip = !selectedChip },
        )
    }
}