package com.giraffe.presentation.authentication.screens.onboarding.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R
import com.giraffe.presentation.authentication.screens.onboarding.screen.OnboardingInteractionListener
import kotlinx.coroutines.launch


@Composable
fun NavigationButtons(
    imagePagerState: PagerState,
    textPagerState: PagerState,
    isFirstPage: Boolean,
    isLastPage: Boolean,
    interaction: OnboardingInteractionListener
) {
    val coroutineScope = rememberCoroutineScope()

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
                            imagePagerState.animateScrollToPage(
                                page = imagePagerState.currentPage - 1,
                                animationSpec = spring(
                                    dampingRatio = 0.85f,
                                    stiffness = 100f
                                )
                            )
                        }

                        coroutineScope.launch {
                            textPagerState.animateScrollToPage(
                                page = textPagerState.currentPage - 1,
                                animationSpec = spring(
                                    dampingRatio = 0.85f,
                                    stiffness = 100f
                                )
                            )
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

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Theme.color.button.primary)
                .padding(vertical = 14.dp)
                .animateContentSize(
                    animationSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
                    alignment = Alignment.TopEnd
                )
                .width(if (isLastPage) 145.dp else 48.dp)
                .clickable {
                    coroutineScope.launch {
                        if (!isLastPage) {
                            imagePagerState.animateScrollToPage(
                                page = imagePagerState.currentPage + 1,
                                animationSpec = spring(
                                    dampingRatio = 0.85f,
                                    stiffness = 100f
                                )
                            )
                        } else {
                            interaction.markOnboardingComplete()
                        }
                    }

                    coroutineScope.launch {
                        if (!isLastPage) {
                            textPagerState.animateScrollToPage(
                                page = textPagerState.currentPage + 1,
                                animationSpec = spring(
                                    dampingRatio = 0.85f,
                                    stiffness = 100f
                                )
                            )
                        } else {
                            interaction.markOnboardingComplete()
                        }
                    }
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLastPage) {
                Text(
                    text = stringResource(R.string.get_started),
                    style = Theme.textStyle.body.md.medium,
                    color = Theme.color.background.screen,
                    maxLines = 1,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Icon(
                painter = painterResource(id = Theme.icons.outline.altArrowRight),
                contentDescription = if (isLastPage)
                    stringResource(R.string.get_started)
                else
                    stringResource(R.string.next),
                tint = Theme.color.background.screen
            )
        }
    }
}