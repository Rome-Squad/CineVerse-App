package com.giraffe.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.CTACard
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun AdvertisementSection(
    modifier: Modifier = Modifier,
    title: String = "What Should I Watch?",
    cardTitle: String = "Let Us Choose For You!",
    caption: String = "We’ll help you skip the scroll and go straight to the good stuff.",
    onCardClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CTACard(
            title = cardTitle,
            caption = caption,
            icon = painterResource(Theme.icons.dueTone.magicStick),
            onClick = onCardClick
        )
    }

}

@Preview
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        AdvertisementSection()
    }
}