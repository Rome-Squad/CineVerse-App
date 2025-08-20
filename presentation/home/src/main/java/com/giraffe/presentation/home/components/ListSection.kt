package com.giraffe.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.giraffe.designsystem.composable.Rating
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.Poster
import com.giraffe.presentation.home.utils.shimmerEffect

@Composable
fun ListSection(
    title: String,
    posters: List<Poster>,
    modifier: Modifier = Modifier,
    endText: String? = null,
    paddingHorizontal: Int = 16,
    isLoading: Boolean = false,
    onClickEndText: () -> Unit = {},
    onClickItem: (id: Int, mediaType: MediaType) -> Unit = { _, _ -> }
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SectionTitle(
            modifier = Modifier.padding(horizontal = paddingHorizontal.dp),
            title = title,
            clickableText = if (posters.size >= 10) endText else null,
            onClickableText = onClickEndText
        )
        if (isLoading) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = paddingHorizontal.dp)
            ) {
                items(5) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .height(182.dp)
                                .clip(RoundedCornerShape(Theme.radius.lg))
                                .shimmerEffect()
                                .aspectRatio(0.74f),
                        )
                        Box(
                            modifier = Modifier
                                .height(12.dp)
                                .width(96.dp)
                                .clip(RoundedCornerShape(Theme.radius.lg))
                                .shimmerEffect(),
                        )
                    }
                }
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = paddingHorizontal.dp)
            ) {
                items(items = posters, key = { it.id }) { uiModel ->
                    HomeItemVertically(
                        item = uiModel,
                        modifier = Modifier.width(136.dp),
                        onClick = onClickItem
                    )
                }
            }
        }

    }
}

@Composable
fun HomeItemVertically(
    item: Poster,
    modifier: Modifier = Modifier,
    onClick: (id: Int, type: MediaType) -> Unit
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = dropUnlessResumed {
                    onClick(item.id, item.mediaType)
                }),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.background.card)
                .aspectRatio(0.74f),
            contentAlignment = Alignment.Center
        ) {
            SafeIslamicImage(
                imageUrl = item.posterUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxSize()
            )
            Rating(
                value = item.rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp)
            )
        }

        Text(
            text = item.title,
            style = Theme.textStyle.body.md.medium,
            color = Theme.color.shade.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}