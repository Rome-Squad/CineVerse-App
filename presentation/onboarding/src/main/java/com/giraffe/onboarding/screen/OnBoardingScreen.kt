package com.giraffe.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.theme.Theme
import com.giraffe.onboarding.R
import com.giraffe.onboarding.composable.ImagePager

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    // val state by viewModel.state.collectAsState()

    val images: List<Int> = listOf(
        R.drawable.onboard1,
        R.drawable.onboard2,
        R.drawable.onboard3
    )

    OnBoardingContent(
        modifier = modifier,
        onBoardingImageList = images
    )
}

@Composable
fun OnBoardingContent(
    modifier: Modifier,
    onBoardingImageList: List<Int>
) {
    val pagerState = rememberPagerState(pageCount = { onBoardingImageList.size })
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        ImagePager(
            modifier = Modifier,
            pagerState = pagerState,
            images = onBoardingImageList,
        )
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    val images: List<Int> = listOf(
        R.drawable.onboard1,
        R.drawable.onboard2,
        R.drawable.onboard3
    )
    OnBoardingContent(
        modifier = Modifier,
        onBoardingImageList = images
    )
}