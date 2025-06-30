package com.giraffe.profile.history.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun RatedMovie(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RateSection(modifier = Modifier.fillMaxWidth(), rate = 3.0, date = "Apr 12, 2025")
        Box(
            modifier = Modifier
                .height(88.dp)
                .fillMaxWidth()
                .background(Color.Green, shape = RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun RateSection(modifier: Modifier = Modifier, rate: Double, date: String) {
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
        Text(
            text = "on $date",
            style = Theme.textStyle.body.sm.medium,
            color = Theme.color.shade.primary
        )
    }
}

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

@Preview
@Composable
private fun Preview() {
    CineVerseTheme {
        //Stars(rate = 8.0)
        //RateSection(rate = 4.0, date = "Apr 12, 2025")
        RatedMovie(modifier = Modifier.width(328.dp))
    }
}