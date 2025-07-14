package com.giraffe.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
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
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.utils.imageSourceToPainter

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainDetails(
    actorImage: Painter,
    actorName: String,
    actorBirthday: String,
    actorPlaceOfBirth: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onYoutubeClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onInstagramClick: () -> Unit,

    ) {
    val singleSpace = " "
    val key = "_KEY"
    with(sharedTransitionScope) {
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
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = actorImage.toString() + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
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
                        .fillMaxHeight()
                    ,
                    painter = actorImage,
                    contentDescription = stringResource(R.string.actor_image)
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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

                    IconTextBox(
                        icon = painterResource(Theme.icons.outline.cake),
                        contentDescription = stringResource(R.string.birthday_cake_icon),
                        text = stringResource(R.string.actor_birthday) + singleSpace + actorBirthday,
                    )

                    IconTextBox(
                        icon = painterResource(Theme.icons.outline.location),
                        contentDescription = stringResource(R.string.location_icon),
                        text = stringResource(R.string.place_of_birth) + singleSpace + actorPlaceOfBirth,
                    )

                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SocialMediaComponent(
                    modifier = Modifier
                        .weight(1f),
                    image = painterResource(Theme.icons.colored.youtube),
                    name = stringResource(R.string.youtube),
                    contentDescription = stringResource(R.string.youtube_icon),
                    onClick = onYoutubeClick
                )
                SocialMediaComponent(
                    modifier = Modifier
                        .weight(1f),
                    image = painterResource(Theme.icons.colored.facebook),
                    name = stringResource(R.string.facebook),
                    contentDescription = stringResource(R.string.facebook_icon),
                    onClick = onFacebookClick
                )
                SocialMediaComponent(
                    modifier = Modifier
                        .weight(1f),
                    image = painterResource(Theme.icons.colored.instagram),
                    name = stringResource(R.string.instagram),
                    contentDescription = stringResource(R.string.instagram_icon),
                    onClick = onInstagramClick
                )
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
    actorImage: Painter,
    actorName: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    ) {
    val key = "_KEY"
    with(sharedTransitionScope) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .background(Theme.color.background.screen)
                .padding(top = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 40.dp)
            ) {
                Image(
                    painter = actorImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = actorImage.toString() + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(40.dp)
                        .clip(CircleShape)
                )
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
            Divider(thickness = 1.dp, color = Theme.color.stroke.primary)
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
                            actorImage = R.drawable.gallery_item2.imageSourceToPainter(),
                            actorName = "Christian Bale",
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                        )

                        false -> MainDetails(
                            modifier = Modifier.padding(top = 72.dp),
                            actorImage = R.drawable.gallery_item2.imageSourceToPainter(),
                            actorName = "Christian Bale",
                            actorBirthday = "Jan 30, 1974",
                            actorPlaceOfBirth = "Cardiff, Wales, UK",
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onYoutubeClick = {},
                            onFacebookClick = {},
                            onInstagramClick = {}
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
