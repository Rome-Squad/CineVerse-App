package com.giraffe.presentation.authentication.screens.onboarding.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.authentication.screens.onboarding.screen.OnBoardingPage
import com.giraffe.presentation.authentication.screens.onboarding.screen.OnboardingInteractionListener
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnBoardingFooter(
    pagerState: PagerState,
    pages: List<OnBoardingPage>,
    modifier: Modifier = Modifier,
    interaction: OnboardingInteractionListener,
    direction: Int
) {
    val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
    val isFirstPage = pagerState.currentPage == 0

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.color.background.card)
            .padding(top = 32.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        AnimatedContent(
            targetState = pagerState.currentPage,
            transitionSpec = {
                val animSpec = spring<IntOffset>(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )

                if (direction > 0) {
                    (slideInHorizontally(animSpec) { it / 2 } + fadeIn()) with
                            (slideOutHorizontally(animSpec) { -it / 2 } + fadeOut())
                } else {
                    (slideInHorizontally(animSpec) { -it / 2 } + fadeIn()) with
                            (slideOutHorizontally(animSpec) { it / 2 } + fadeOut())
                }
            },
            label = stringResource(R.string.footertextanimation)
        ) { pageIndex ->
            AnimatedPageText(pages[pageIndex])
        }

        NavigationButtons(
            pagerState = pagerState,
            isFirstPage = isFirstPage,
            isLastPage = isLastPage,
            direction = direction,
            interaction = interaction
        )
    }
}