package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun Rating(
    value: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(Theme.color.background.card)
            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = (((value * 10).toInt()).toFloat() / 10).toString(),
            style = Theme.textStyle.label.md.medium,
            color = Theme.color.shade.primary
        )
        Icon(
            painter = painterResource(Theme.icons.dueTone.star),
            contentDescription = null,
            tint = Theme.color.additional.primary.yellow,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        Rating(
            value = 7.5f,
        )
    }
}