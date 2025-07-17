package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.screens.moviedetails.model.CastUi
import com.giraffe.details.utils.imageSourceToPainter


@Composable
fun StarCastSection(
    title: String,
    onShowMoreClick: () -> Unit,
    castList: List<CastUi>,
    modifier: Modifier = Modifier
) {
    val chunkedList = castList.chunked(2)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Theme.color.shade.primary,
                style = Theme.textStyle.title.sm,
                modifier = Modifier.padding(start = 16.dp),
            )

            Text(
                text = stringResource(R.string.show_more),
                color = Theme.color.brand.primary,
                modifier = Modifier
                    .padding(start = 12.dp, end = 16.dp)
                    .clickable { onShowMoreClick() },
                style = Theme.textStyle.body.md.medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chunkedList) { pair ->
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    pair.forEach { cast ->
                        CastCard(
                            actorName = cast.name,
                            character = cast.role,
                            actorImage = cast.urlImage?.imageSourceToPainter()?: painterResource(R.drawable.reviewer),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CastCard(
    actorName: String, character: String, actorImage: Painter, modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.color.background.card),
        modifier = modifier.width(200.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = actorImage,
                contentDescription = "$actorName image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            bottomEnd = 0.dp
                        )
                    )
            )

            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.5.dp)) {
                Text(
                    text = actorName,
                    color = Theme.color.shade.primary,
                    style = Theme.textStyle.body.md.medium,
                    maxLines = 1,
                    minLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = character,
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.body.sm.regular
                )
            }
        }
    }
}


@Composable
@Preview(
    name = "StarCastSection Light", showBackground = true, apiLevel = 34
)
fun PreviewStarCastSectionLight() {
    CineVerseTheme(isDarkTheme = false) {
        StarCastSection(
            title = "Star Cast", onShowMoreClick = {}, castList = sampleCastList()
        )
    }
}

@Composable
@Preview(
    name = "StarCastSection Dark", showBackground = false, apiLevel = 34
)
fun PreviewStarCastSectionDark() {
    CineVerseTheme(isDarkTheme = true) {
        StarCastSection(
            title = "Star Cast", onShowMoreClick = {}, castList = sampleCastList()
        )
    }
}

@Composable
fun sampleCastList(): List<CastUi> {
    val image = painterResource(
        id = R.drawable.reviewer
    )
    return listOf(
        CastUi("Robert Downey Jr.", "Iron Man", image.toString()),
        CastUi("Robert Downey Jr.", "Iron Man", image.toString()),
        CastUi("Robert Downey Jr.", "Iron Man", image.toString()),
        CastUi("Robert Downey Jr.", "Iron Man", image.toString()),
    )
}

@Composable
@Preview(
    name = "CastCard Preview", showBackground = false, apiLevel = 34
)
fun PreviewCastCard() {
    CineVerseTheme(isDarkTheme = false) {
        CastCard(
            actorName = "Robert Downey", character = "Iron Man", actorImage = painterResource(
                id = R.drawable.reviewer
            )
        )
    }
}

@Composable
@Preview(
    name = "CastCard Preview Dark", showBackground = false, apiLevel = 34
)
fun PreviewCastCardDark() {
    CineVerseTheme(isDarkTheme = true) {
        CastCard(
            actorName = "Robert Downey", character = "Iron Man", actorImage = painterResource(
                id = R.drawable.reviewer
            )
        )
    }
}