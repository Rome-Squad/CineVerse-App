package com.giraffe.details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun MainInfoCardSection(
    title: String,
    genres: String,
    rating: String,
    duration: String,
    releaseDate: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(com.giraffe.details.R.string.watch),
                    style = Theme.textStyle.label.md.medium,
                    color = Theme.color.brand.primary

                )
                Text(
                    text = title,
                    style = Theme.textStyle.title.md,
                    color = Theme.color.shade.primary
                )
             Text(
                    text = genres,
                    style = Theme.textStyle.body.sm.medium,
                 color = Theme.color.shade.secondary


             )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MovieInfoIconWithText(
                    icon = painterResource(Theme.icons.dueTone.star),
                    text = rating,
                    tint = Theme.color.additional.primary.yellow
                )
                MovieInfoIconWithText(
                    icon = painterResource(Theme.icons.dueTone.clock),
                    text = duration
                )
                MovieInfoIconWithText(
                    icon = painterResource(Theme.icons.dueTone.calendar),
                    text = releaseDate
                )
            }
        }

        Column(
            modifier = Modifier.height(92.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundedIconButton(
                icon = painterResource(Theme.icons.dueTone.add),
                backgroundColor = Color(0xFF24263B)
            )
            RoundedIconButton(
                icon = painterResource(Theme.icons.dueTone.play),
                backgroundColor = Color(0xFF8C9EFF),
                iconPaddingTop = 12.dp,
                iconPaddingBottom = 12.dp
            )
        }
    }
}


@Composable
fun MovieInfoIconWithText(
    icon: Painter,
    text: String,
    tint: Color = Color(0xFFA4A4AA)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = tint
        )
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFFA4A4AA)
        )
    }
}

@Composable
fun RoundedIconButton(
    icon: Painter,
    backgroundColor: Color,
    iconPaddingTop: Dp = 0.dp,
    iconPaddingBottom: Dp = 0.dp
) {
    IconButton(
        onClick = {},
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(top = iconPaddingTop, bottom = iconPaddingBottom)
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewMainInfoCardSection() {

    CineVerseTheme (
        isDarkTheme = true
    ) {
        MainInfoCardSection(
            modifier = Modifier
                .fillMaxWidth(),
            title = "The Dark Knight",
            genres = "Drama, Action, Crime, Thriller",
            rating = "8.5",
            duration = "2h 32m",
            releaseDate = "2008, Jul 18"
        )

    }
}