package com.giraffe.presentation.authentication.screens.onboarding.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.screens.onboarding.screen.OnBoardingPage
import com.giraffe.presentation.authentication.screens.onboarding.screen.OnboardingInteractionListener


@Composable
fun OnBoardingFooter(
    imagePagerState: PagerState,
    textPagerState: PagerState,
    pages: List<OnBoardingPage>,
    modifier: Modifier = Modifier,
    interaction: OnboardingInteractionListener
) {
    val isLastPage = imagePagerState.currentPage == imagePagerState.pageCount - 1
    val isFirstPage = imagePagerState.currentPage == 0

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.color.background.card)
            .padding(top = 32.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        HorizontalPager(
            state = textPagerState,
            beyondViewportPageCount = textPagerState.pageCount,
            userScrollEnabled = false,
            modifier = Modifier.wrapContentSize()
        ) { pageIndex ->
            AnimatedPageText(pages[pageIndex])
        }

        NavigationButtons(
            imagePagerState = imagePagerState,
            textPagerState = textPagerState,
            isFirstPage = isFirstPage,
            isLastPage = isLastPage,
            interaction = interaction
        )
    }
}