package com.giraffe.authentication.onboarding.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.authentication.R
import com.giraffe.authentication.onboarding.screen.OnboardingInteractionListener
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import kotlinx.coroutines.launch


@Composable
fun NavigationButtons(
    pagerState: PagerState,
    isFirstPage: Boolean,
    isLastPage: Boolean,
    direction: Int,
    interaction: OnboardingInteractionListener
) {
    val coroutineScope = rememberCoroutineScope()
    val buttonTargetWidth = if (isLastPage) 145.dp else 48.dp
    val animatedWidth by animateDpAsState(
        targetValue = buttonTargetWidth,
        animationSpec = tween(durationMillis = 300),
        label = stringResource(R.string.animatedwidth)
    )
    val animatedButtonOffsetX = remember { Animatable(0f) }

    LaunchedEffect(pagerState.currentPage) {
        animatedButtonOffsetX.snapTo(if (direction > 0) 40f else -40f)
        animatedButtonOffsetX.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!isFirstPage) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Theme.color.button.secondary)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = Theme.icons.outline.altArrowLeft),
                    contentDescription = stringResource(R.string.back),
                    tint = Theme.color.shade.primary
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .height(48.dp)
                .width(animatedWidth)
                .graphicsLayer { translationX = animatedButtonOffsetX.value }
                .clip(RoundedCornerShape(12.dp))
                .background(Theme.color.button.primary)
                .clickable {
                    coroutineScope.launch {
                        if (!isLastPage) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }else{
                            interaction.markOnboardingComplete()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = isLastPage,
                transitionSpec = {
                    fadeIn(tween(300, delayMillis = 90)).togetherWith(fadeOut(tween(90)))
                },
                label = stringResource(R.string.nextorgetstarted)
            ) { showText ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (showText) {
                        Text(
                            text = stringResource(R.string.get_started),
                            style = Theme.textStyle.body.md.medium,
                            color = Theme.color.background.screen,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    Icon(
                        painter = painterResource(id = Theme.icons.outline.altArrowRight),
                        contentDescription = if (showText)
                            stringResource(R.string.get_started)
                        else
                            stringResource(R.string.next),
                        tint = Theme.color.background.screen
                    )
                }
            }
        }
    }
}