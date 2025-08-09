package com.giraffe.presentation.authentication.screens.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.presentation.authentication.screens.onboarding.composable.ImagePager
import com.giraffe.presentation.authentication.screens.onboarding.composable.OnBoardingFooter
import com.giraffe.presentation.authentication.utils.mapExceptionToStringRes
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is OnboardingEffect.NavigateToLogin -> navigateToLoginScreen()
                is OnboardingEffect.ShowError -> {
                     mapExceptionToStringRes(effect.throwable)

                }
            }
        }
    }

    OnBoardingContent(
        modifier = modifier,
        pages = getPages(),
        interaction = viewModel
    )
}

@Composable
private fun OnBoardingContent(
    modifier: Modifier = Modifier,
    pages: List<OnBoardingPage>,
    interaction: OnboardingInteractionListener
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scrollState = rememberScrollState()
    val previousPage = remember { mutableIntStateOf(pagerState.currentPage) }
    val direction = if (pagerState.currentPage > previousPage.intValue) 1 else -1

    LaunchedEffect(pagerState.currentPage) {
        previousPage.intValue = pagerState.currentPage
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = modifier
                .padding(top = 16.dp)
                .verticalScroll(scrollState)
                .navigationBarsPadding()
                .background(Theme.color.background.screen)
        ) {
            ImagePager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 69.dp),
                pagerState = pagerState,
                images = pages.map { it.imageRes },
            )

            OnBoardingFooter(
                pagerState = pagerState,
                pages = pages,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                interaction = interaction,
                direction = direction
            )
        }
    }
}

data class OnBoardingPage(
    val imageRes: Int,
    val title: String,
    val subtitle: String
)

@Composable
private fun getPages(): List<OnBoardingPage> {
    return listOf(
        OnBoardingPage(
            imageRes = R.drawable.onboard1,
            title = stringResource(R.string.welcome_to_your_movie_universe),
            subtitle = stringResource(R.string.discover_track_and_rate_your_favorite_movies_series)
        ),
        OnBoardingPage(
            imageRes = R.drawable.onboard2,
            title = stringResource(R.string.track_everything),
            subtitle = stringResource(R.string.your_watchlist_your_ratings_all_in_one_place)
        ),
        OnBoardingPage(
            imageRes = R.drawable.onboard3,
            title = stringResource(R.string.personalized_recommendations),
            subtitle = stringResource(R.string.answer_fun_questions_to_get_handpicked_recommendations)
        )
    )
}
