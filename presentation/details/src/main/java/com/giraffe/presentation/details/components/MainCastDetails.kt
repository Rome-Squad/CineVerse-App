package com.giraffe.presentation.details.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.model.SocialMediaUi
import com.giraffe.presentation.details.utils.getIcon
import kotlin.math.max

@Composable
fun MainCastDetails(
    actorName: String,
    actorBirthday: String,
    actorPlaceOfBirth: String,
    socialMediaUiList: List<SocialMediaUi>,
    onLinkClick: (String) -> Unit,
    actorImageUrl: String?,
    modifier: Modifier = Modifier,
    animationProgress: Float = 0f
) {
    val inverseAnimationProgress = 1f - animationProgress

    val radiusXl = Theme.radius.xl
    val radiusS = Theme.radius.s
    val shapeImage = remember(animationProgress) {
        RoundedCornerShape(
            topStart = radiusXl + (4.dp * animationProgress),
            topEnd = radiusS + (12.dp * animationProgress),
            bottomStart = radiusS + (12.dp * animationProgress),
            bottomEnd = radiusXl + (4.dp * animationProgress)
        )
    }
    val singleSpace = remember { " " }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(Theme.radius.xxl))
            .background(Theme.color.background.card.copy(inverseAnimationProgress))
            .padding(vertical = 16.dp * inverseAnimationProgress),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp)
                .animateContentSize()
        ) {
            actorImageUrl?.let {
                SafeIslamicImage(
                    imageUrl = it,
                    contentDescription = actorName,
                    contentScale = ContentScale.Crop,
                    hasSensitiveText = false,
                    modifier = Modifier
                        .padding(
                            top = 8.dp * animationProgress,
                            bottom = 8.dp * animationProgress,
                            end = 12.dp - 4.dp * animationProgress,
                            start = 16.dp * animationProgress
                        )
                        .size(
                            height = 80.dp - (40.dp * animationProgress),
                            width = 64.dp - (24.dp * animationProgress)
                        )
                        .clip(shape = shapeImage),
                    placeHolderTint = Theme.color.brand.secondary,
                    placeholderModifier = Modifier
                        .background(Theme.color.background.card)
                        .fillMaxSize()
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy((8 * inverseAnimationProgress).dp)) {
                Text(
                    style = Theme.textStyle.title.md,
                    color = Theme.color.shade.primary,
                    text = actorName
                )

                val iconTextHeight = max(
                    Theme.textStyle.label.md.regular.fontSize.value + 4,
                    16f
                )

                if (actorBirthday.isNotBlank()) {
                    IconTextBox(
                        icon = painterResource(Theme.icons.outline.cake),
                        contentDescription = stringResource(R.string.birthday_cake_icon),
                        text = stringResource(R.string.actor_birthday) + singleSpace + actorBirthday,
                        modifier = Modifier
                            .height((iconTextHeight * inverseAnimationProgress).dp)
                            .alpha(inverseAnimationProgress)
                    )
                }

                if (actorPlaceOfBirth.isNotBlank()) {
                    IconTextBox(
                        icon = painterResource(Theme.icons.outline.location),
                        contentDescription = stringResource(R.string.location_icon),
                        text = stringResource(R.string.place_of_birth) + singleSpace + actorPlaceOfBirth,
                        modifier = Modifier
                            .height((iconTextHeight * inverseAnimationProgress).dp)
                            .alpha(inverseAnimationProgress)
                    )
                }
            }
        }

        if (socialMediaUiList.isNotEmpty()) {
            val socialMediaItemHeight = max(
                Theme.textStyle.label.md.medium.fontSize.value + 4,
                24f
            ) + 16f

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(socialMediaUiList) {
                    SocialMediaComponent(
                        image = it.platform.getIcon(),
                        name = stringResource(it.name),
                        contentDescription = stringResource(it.contentDescription),
                        onClick = { onLinkClick(it.url) },
                        modifier = Modifier
                            .height((socialMediaItemHeight * inverseAnimationProgress).dp)
                            .alpha(inverseAnimationProgress)
                    )
                }
            }
        }
    }
}


@Composable
fun SocialMediaComponent(
    modifier: Modifier = Modifier,
    image: Painter,
    name: String,
    contentDescription: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(Theme.radius.full))
            .background(Theme.color.shade.quinary)
            .clickable(onClick = onClick)
            .wrapContentSize()
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