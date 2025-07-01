package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@Composable
fun MainDetails(
    modifier: Modifier = Modifier,
    actorImage: Painter,
    actorName: String,
    actorBirthday: String,
    actorPlaceOfBirth: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(Theme.radius.xxl))
            .background(Theme.color.background.card)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(80.dp)
                    .width(64.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = Theme.radius.xl,
                            topEnd = Theme.radius.s,
                            bottomStart = Theme.radius.s,
                            bottomEnd = Theme.radius.xl
                        )
                    )
                    .fillMaxHeight(),
                painter = actorImage,
                contentDescription = stringResource(R.string.actor_image)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    style = Theme.textStyle.title.md,
                    color = Theme.color.shade.primary,
                    text = actorName
                )

                IconTextBox(
                    icon = painterResource(Theme.icons.outline.cake),
                    contentDescription = stringResource(R.string.birthday_cake_icon),
                    text = stringResource(R.string.actor_birthday, actorBirthday)
                )

                IconTextBox(
                    icon = painterResource(Theme.icons.outline.location),
                    contentDescription = stringResource(R.string.location_icon),
                    text = stringResource(R.string.place_of_birth, actorPlaceOfBirth)
                )

            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SocialMediaComponent(
                modifier = Modifier.weight(1f),
                image = painterResource(Theme.icons.colored.youtube),
                name = stringResource(R.string.youtube),
                contentDescription = stringResource(R.string.youtube_icon)
            )
            SocialMediaComponent(
                modifier = Modifier.weight(1f),
                image = painterResource(Theme.icons.colored.facebook),
                name = stringResource(R.string.facebook),
                contentDescription = stringResource(R.string.facebook_icon)
            )
            SocialMediaComponent(
                modifier = Modifier.weight(1f),
                image = painterResource(Theme.icons.colored.instagram),
                name = stringResource(R.string.instgram),
                contentDescription = stringResource(R.string.instagram_icon)
            )
        }
    }
}


@Composable
fun SocialMediaComponent(
    modifier: Modifier = Modifier,
    image: Painter,
    name: String,
    contentDescription: String
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(Theme.radius.full))
            .background(Theme.color.shade.quinary)
            .padding(
                start = 10.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = contentDescription
        )
        Text(
            text = name,
            style = Theme.textStyle.label.md.medium,
            color = Theme.color.shade.primary
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainDetailsPreview() {
    CineVerseTheme(isDarkTheme = true) {
        MainDetails(
            actorImage = painterResource(R.drawable.gallery_item),
            actorName = "Christian Bale",
            actorBirthday = "Jan 30, 1970",
            actorPlaceOfBirth = "Cardiff, Wales, UK"
        )
    }
}
