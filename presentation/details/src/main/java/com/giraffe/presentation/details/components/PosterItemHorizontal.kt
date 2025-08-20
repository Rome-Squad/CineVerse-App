package com.giraffe.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.Rating
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.details.components.uimodel.Poster

@Composable
fun PosterItemHorizontal(
    movie: Poster,
    modifier: Modifier = Modifier,
    onClickPoster: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .clickable(onClick = onClickPoster),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SafeIslamicImage(
                imageUrl = movie.imageUrl,
                contentDescription = movie.name,
                hasSensitiveText = false,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(64.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg
                        )
                    ),
                placeHolderTint = Theme.color.brand.secondary,
                placeholderTextStyle = Theme.textStyle.body.sm.medium.merge(color = Color(0xFFE1E1E3)),
                placeholderModifier = Modifier
                    .background(color = Theme.color.brand.tertiary)
                    .height(88.dp)
                    .width(64.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = movie.name,
                        style = Theme.textStyle.body.md.medium,
                        color = Theme.color.shade.primary
                    )
                    if (movie.genres != null) {
                        Text(
                            text = movie.genres,
                            style = Theme.textStyle.body.sm.regular,
                            color = Theme.color.shade.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }


                if (movie.time != null || movie.date != null) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (movie.time != null) {
                            IconWithText(
                                icon = painterResource(Theme.icons.dueTone.clock),
                                text = movie.time
                            )
                        }
                        if (movie.date != null) {
                            IconWithText(
                                icon = painterResource(Theme.icons.dueTone.calendar),
                                text = movie.date
                            )
                        }
                    }
                }
            }
        }

        if (movie.rating != 0f) {
            Rating(
                hasBackground = false,
                value = movie.rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        end = 12.dp,
                        top = 12.dp
                    )
            )
        }
    }
}

@Composable
private fun IconWithText(icon: Painter, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.clock_icon),
            tint = Theme.color.shade.secondary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = Theme.textStyle.label.md.regular,
            color = Theme.color.shade.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        PosterItemHorizontal(
            movie = Poster(
                id = 1,
                name = "The Flash",
                imageUrl = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
                rating = 7.5f,
                genres = "Drama, Action, Crime, ThrillerDrama, Action, Crime, ThrillerDrama, Action, Crime, ThrillerDrama, Action, Crime, Thriller",
                time = "2h 32m",
                date = "2008, Jul 18"
            ),
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .height(88.dp)
        )
    }
}