package com.giraffe.presentation.details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.CustomCard
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.models.CastUi
import com.giraffe.imageviewer.component.SafeIslamicImage


@Composable
fun StarCastSection(
    title: String,
    onCastClick: (personId: Int) -> Unit,
    castList: List<CastUi>,
    modifier: Modifier = Modifier,
) {
    val sizeOfChunkedList = if (castList.size > 2) 2 else 1
    val chunkedList = castList.chunked(sizeOfChunkedList)
    if (castList.isNotEmpty()) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = title,
                color = Theme.color.shade.primary,
                style = Theme.textStyle.title.sm,
                modifier = Modifier.padding(start = 16.dp),
            )

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
                                actorImage = cast.urlImage.toString(),
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(Theme.radius.lg))
                                    .clickable(
                                        onClick = { onCastClick(cast.id) }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CastCard(
    actorName: String,
    character: String,
    actorImage: String,
    modifier: Modifier = Modifier
) {
    CustomCard(
        shape = RoundedCornerShape(Theme.radius.lg),
        modifier = modifier.width(200.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            SafeIslamicImage(
                imageUrl = actorImage,
                contentDescription = "$actorName image",
                hasSensitiveText = false,
                modifier = Modifier
                    .size(64.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            bottomEnd = 0.dp
                        )
                    ),
                contentScale = ContentScale.Crop,
                placeHolderTint = Theme.color.brand.secondary,
                placeholderModifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Theme.color.stroke.primary,
                        shape = RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg,
                            bottomEnd = Theme.radius.lg
                        )
                    )
            )

            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.5.dp)) {
                Text(
                    text = actorName,
                    color = Theme.color.shade.primary,
                    style = Theme.textStyle.body.md.medium,
                    minLines = 1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = character,
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.body.sm.regular,
                    minLines = 1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}