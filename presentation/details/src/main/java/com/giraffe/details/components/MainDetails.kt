package com.giraffe.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.screens.castDetails.state.SocialMediaUi
import com.giraffe.details.screens.castDetails.state.getIcon
import com.giraffe.imageviewer.component.SafeIslamicImage

@Composable
fun MainDetails(
    actorName: String,
    actorBirthday: String,
    actorPlaceOfBirth: String,
    socialMediaUiList: List<SocialMediaUi>,
    onLinkClick: (String) -> Unit,
    actorImageUrl: String?,
    modifier: Modifier = Modifier,
    animationProgress: Float = 0f
) {
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
            .background(Theme.color.background.card.copy(1f - animationProgress))
            .padding(vertical = 16.dp * (1 - animationProgress)),
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
                        .clip(shape = shapeImage)
                ) {
                    Icon(
                        painter = painterResource(Theme.icons.dueTone.image),
                        contentDescription = actorName,
                        tint = Theme.color.brand.secondary,
                        modifier = Modifier
                            .padding(8.dp * animationProgress)
                            .size(
                                height = 80.dp - (40.dp * animationProgress),
                                width = 64.dp - (24.dp * animationProgress)
                            )
                            .border(
                                width = 1.dp,
                                color = Theme.color.stroke.primary,
                                shape = shapeImage
                            )
                            .clip(shape = shapeImage)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    style = Theme.textStyle.title.md,
                    color = Theme.color.shade.primary,
                    text = actorName
                )

                if (actorBirthday.isNotBlank() && animationProgress == 0f) {
                    IconTextBox(
                        icon = painterResource(Theme.icons.outline.cake),
                        contentDescription = stringResource(R.string.birthday_cake_icon),
                        text = stringResource(R.string.actor_birthday) + singleSpace + actorBirthday,
                    )
                }

                if (actorPlaceOfBirth.isNotBlank() && animationProgress == 0f) {
                    IconTextBox(
                        icon = painterResource(Theme.icons.outline.location),
                        contentDescription = stringResource(R.string.location_icon),
                        text = stringResource(R.string.place_of_birth) + singleSpace + actorPlaceOfBirth,
                    )
                }
            }
        }

        if (socialMediaUiList.isNotEmpty() && animationProgress == 0f) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(socialMediaUiList) {
                    SocialMediaComponent(
                        image = it.platform.getIcon(),
                        name = stringResource(it.name),
                        contentDescription = stringResource(it.contentDescription),
                        onClick = { onLinkClick(it.url) }
                    )
                }
            }
        }
    }
//    }
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


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainDetailsHeader(
    actorName: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    actorImageUrl: String?,
    modifier: Modifier = Modifier,
) {
    val key = "_KEY"
    with(sharedTransitionScope) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.background.screen)
                .padding(top = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.padding(start = 40.dp)
            ) {
                actorImageUrl?.let {
                    SafeIslamicImage(
                        imageUrl = it,
                        contentDescription = actorName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = actorImageUrl + key),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    {
                        Icon(
                            painter = painterResource(Theme.icons.dueTone.image),
                            contentDescription = actorName,
                            tint = Theme.color.brand.secondary,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    Theme.color.background.card,
                                    shape = CircleShape
                                )
                                .padding(12.dp)
                                .wrapContentSize(),
                        )
                    }
                }
                Text(
                    style = Theme.textStyle.title.md,
                    color = Theme.color.shade.primary,
                    text = actorName,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = actorName + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Theme.color.stroke.primary)
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@PreviewLightDark
fun MainDetailsPreview() {
    CineVerseTheme(isDarkTheme = true) {
        val scrollState = rememberScrollState()
        var isScroll by remember {
            mutableStateOf(false)
        }
        isScroll = scrollState.value > 5
        Box(
            modifier = Modifier
                .background(Theme.color.background.screen)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)

            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 252.dp)
                        .height(2000.dp)
                        .fillMaxWidth()
                        .background(Color.Red)
                )
            }
            SharedTransitionLayout {
                AnimatedContent(
                    isScroll,
                    transitionSpec = {
                        fadeIn(
                            animationSpec = tween(100)
                        ) togetherWith fadeOut(animationSpec = tween(100))
                    },
                    label = "Animated Content"
                ) { targetState ->
                    when (targetState) {
                        true -> MainDetailsHeader(
                            actorImageUrl = "https://image.tmdb.org/t/p/w500/8Xr2d1b6k3Z5a4c7e9z0j5f8f8f8f8f8.jpg",
                            actorName = "Christian Bale",
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                        )

                        false -> MainDetails(
                            actorName = "Christian Bale",
                            actorBirthday = "Jan 30, 1974",
                            actorPlaceOfBirth = "Cardiff, Wales, UK",
                            actorImageUrl = "https://image.tmdb.org/t/p/w500/8Xr2d1b6k3Z5a4c7e9z0j5f8f8f8f8f8.jpg",
                            socialMediaUiList = emptyList(),
                            onLinkClick = {},
                            modifier = Modifier.padding(top = 72.dp),
                        )
                    }
                }
            }
            AppBar(
                showBackButton = true,
                hasBackground = false,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
