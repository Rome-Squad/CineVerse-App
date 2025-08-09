package com.giraffe.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.model.RatedPoster

@Composable
fun RatedItem(
    modifier: Modifier = Modifier,
    ratedPoster: RatedPoster,
    onItemClick: (RatedPoster) -> Unit = {},
    onDeleteClick: (RatedPoster) -> Unit = {}
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RateSection(
            modifier = Modifier.fillMaxWidth(),
            rate = ratedPoster.rating.toDouble(),
        )
        SwipableItem(
            actionButton = {
                DeleteButton(
                    onDeleteClick = { onDeleteClick(ratedPoster) }
                )
            },
        ) {
            PosterItemHorizontal(
                modifier = modifier.fillMaxWidth(),
                movie = ratedPoster.poster,
                onClickPoster = { onItemClick(ratedPoster) }
            )
        }
    }
}

@Composable
fun DeleteButton(modifier: Modifier = Modifier, onDeleteClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .size(48.dp)
            .background(
                color = Theme.color.additional.primary.red,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .clip(RoundedCornerShape(Theme.radius.lg))
            .clickable(onClick = onDeleteClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = modifier.size(20.dp),
            painter = painterResource(Theme.icons.dueTone.trash),
            contentDescription = "trash"
        )
    }
}

@Composable
private fun RateSection(modifier: Modifier = Modifier, rate: Double) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "You gave it",
                style = Theme.textStyle.body.sm.medium,
                color = Theme.color.shade.primary
            )
            Stars(rate = rate)
        }
    }
}

@Composable
private fun Stars(modifier: Modifier = Modifier, rate: Double) {
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