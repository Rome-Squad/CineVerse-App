package com.giraffe.presentation.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun MainInfoCard(
    type: String,
    name: String,
    genres: String,
    rating: Float,
    releaseDate: String,
    modifier: Modifier = Modifier,
    duration: String? = null,
    onClickPlay: () -> Unit = {},
    onClickAdd: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .padding(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth().padding(end = 48.dp)) {
                Text(
                    text = type.uppercase(),
                    style = Theme.textStyle.label.md.medium,
                    color = Theme.color.brand.primary
                )
                Text(
                    text = name,
                    style = Theme.textStyle.title.md,
                    color = Theme.color.shade.primary
                )
                Text(
                    text = genres,
                    style = Theme.textStyle.body.sm.medium,
                    color = Theme.color.shade.secondary,
                    maxLines = 1,
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoIconWithText(
                    icon = painterResource(Theme.icons.dueTone.star),
                    text = "%.1f".format(rating),
                    tint = Theme.color.additional.primary.yellow
                )
                AnimatedVisibility(duration != null) {
                    InfoIconWithText(
                        icon = painterResource(Theme.icons.dueTone.clock),
                        text = duration!!,
                        tint = Theme.color.shade.secondary
                    )
                }
                InfoIconWithText(
                    icon = painterResource(Theme.icons.dueTone.calendar),
                    text = releaseDate,
                    tint = Theme.color.shade.secondary
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(11.5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(Theme.icons.dueTone.play),
                contentDescription = null,
                tint = Theme.color.brand.tertiary,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.color.button.primary)
                    .padding(10.dp)
                    .clickable(onClick = onClickPlay)
            )
            Icon(
                painter = painterResource(Theme.icons.dueTone.add),
                contentDescription = null,
                tint = Theme.color.shade.primary,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.color.button.secondary)
                    .padding(10.dp)
                    .clickable(onClick = onClickAdd)
            )
        }
    }
}


@Composable
private fun InfoIconWithText(
    icon: Painter,
    text: String,
    tint: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
           painter = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = tint
        )
        Text(
            text = text,
            style = Theme.textStyle.label.md.regular,
            color = Theme.color.shade.secondary
        )
    }
}


@Composable
@Preview()
fun PreviewMainInfoCardSection() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        MainInfoCard(
            name = "The Dark Knight",
            genres = "Drama, Action, Crime, Thriller",
            rating = 8.5f,
            duration = "2h 32m",
            releaseDate = "2008, Jul 18",
            type = "MOVIE",
            modifier = Modifier.width(328.dp)
        )

    }
}