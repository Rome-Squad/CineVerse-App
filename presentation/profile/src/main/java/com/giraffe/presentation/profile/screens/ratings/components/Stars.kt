package com.giraffe.presentation.profile.screens.ratings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.theme.Theme

@Composable
fun Stars(modifier: Modifier = Modifier, rate: Double) {
    var remainRate = rememberSaveable { rate.toInt() }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            val iconRes = if (remainRate > 0) Theme.icons.bold.star else Theme.icons.outline.star
            val iconTint =
                if (remainRate > 0) Theme.color.additional.primary.yellow else Theme.color.shade.tertiary
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(iconRes),
                contentDescription = "star",
                tint = iconTint,
            )
            remainRate--
        }
    }
}