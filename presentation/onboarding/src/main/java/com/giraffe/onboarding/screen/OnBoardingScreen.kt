package com.giraffe.onboarding.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.onboarding.R
import com.giraffe.onboarding.composable.ImagePager
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val pages = listOf(
        OnBoardingPage(
            imageRes = R.drawable.onboard1,
            title = "Welcome to Your Movie Universe",
            subtitle = "Discover, track, and rate your favorite movies & series."
        ),
        OnBoardingPage(
            imageRes = R.drawable.onboard2,
            title = "Track Everything",
            subtitle = "Your Watchlist. Your Ratings. All in One Place."
        ),
        OnBoardingPage(
            imageRes = R.drawable.onboard3,
            title = "Personalized Recommendations",
            subtitle = "Answer fun questions to get handpicked recommendations."
        )
    )

    OnBoardingContent(
        modifier = modifier,
        pages = pages
    )
}

@Composable
fun OnBoardingContent(
    modifier: Modifier = Modifier,
    pages: List<OnBoardingPage>
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
        Column(
            modifier = modifier
                .padding(top = 56.dp)
                .verticalScroll(scrollState)
                .background(Theme.color.background.screen)
                .systemBarsPadding()
        ) {
            ImagePager(
                modifier = Modifier
                    .fillMaxWidth(),
                pagerState = pagerState,
                images = pages.map { it.imageRes }
            )

            OnBoardingFooter(
                pagerState = pagerState,
                pages = pages,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnBoardingFooter(
    pagerState: PagerState,
    pages: List<OnBoardingPage>,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
    val isFirstPage = pagerState.currentPage == 0
    val currentPage = pages[pagerState.currentPage]

    var previousPage by remember { mutableIntStateOf(pagerState.currentPage) }
    val direction = if (pagerState.currentPage > previousPage) 1 else -1

    LaunchedEffect(pagerState.currentPage) {
        previousPage = pagerState.currentPage
    }

    val buttonTargetWidth = if (isLastPage) 145.dp else 48.dp
    val animatedWidth by animateDpAsState(
        targetValue = buttonTargetWidth,
        animationSpec = tween(durationMillis = 300),
        label = "AnimatedWidth"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.color.background.card)
            .padding(top = 32.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {

        AnimatedContent(
            targetState = currentPage,
            transitionSpec = {
                val animSpec = spring<IntOffset>(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )

                if (direction > 0) {
                    (slideInHorizontally(animationSpec = animSpec) { it / 2 } + fadeIn()) with
                            (slideOutHorizontally(animationSpec = animSpec) { -it / 2 } + fadeOut())
                } else {
                    (slideInHorizontally(animationSpec = animSpec) { -it / 2 } + fadeIn()) with
                            (slideOutHorizontally(animationSpec = animSpec) { it / 2 } + fadeOut())
                }
            },
            label = "FooterTextAnimation"
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(71.dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = page.title,
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = Theme.textStyle.title.md,
                    color = Theme.color.shade.primary,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                Text(
                    text = page.subtitle,
                    style = Theme.textStyle.body.md.medium,
                    color = Theme.color.shade.secondary,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
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
                        contentDescription = "Back",
                        tint = Theme.color.shade.primary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
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

            Box(
                modifier = Modifier
                    .height(48.dp)
                    .width(animatedWidth)
                    .graphicsLayer {
                        translationX = animatedButtonOffsetX.value
                    }
                    .width(animatedWidth)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Theme.color.button.primary)
                    .clickable {
                        coroutineScope.launch {
                            if (!isLastPage) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                // TODO: Navigate to home screen
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = isLastPage,
                    transitionSpec = {
                        fadeIn(tween(300, delayMillis = 90)) with fadeOut(tween(90))
                    },
                    label = "NextOrGetStarted"
                ) { showText ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (showText) {
                            Text(
                                text = "Get Started",
                                style = Theme.textStyle.body.md.medium,
                                color = Theme.color.background.screen,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }

                        Icon(
                            painter = painterResource(id = Theme.icons.outline.altArrowRight),
                            contentDescription = if (showText) "Get Started" else "Next",
                            tint = Theme.color.background.screen
                        )
                    }
                }
            }
        }
    }
}

data class OnBoardingPage(
    val imageRes: Int,
    val title: String,
    val subtitle: String
)
