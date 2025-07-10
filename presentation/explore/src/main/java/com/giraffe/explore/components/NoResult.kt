package com.giraffe.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun NoResult() {

    Image(
        modifier = Modifier
            .padding(18.dp)
            .size(28.dp),
        painter = painterResource(Theme.icons.dueTone.search),
        contentDescription = "Search Icon",
        colorFilter = ColorFilter.tint(Theme.color.brand.primary)
    )
    BasicText(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 8.dp),
        text = "Nothing Found!",
        style = Theme.textStyle.title.sm.copy(
            color = Theme.color.shade.primary
        )
    )
    BasicText(
        modifier = Modifier.width(240.dp),
        text = "We searched the entire universe… but found nothing matching your query. Try something else?",
        style = Theme.textStyle.body.sm.medium.copy(
            color = Theme.color.shade.secondary,
            textAlign = TextAlign.Center
        ),
    )
}

@Preview()
@Composable
private fun NoResultPreview() {
    CineVerseTheme {
        NoResult()
    }
}