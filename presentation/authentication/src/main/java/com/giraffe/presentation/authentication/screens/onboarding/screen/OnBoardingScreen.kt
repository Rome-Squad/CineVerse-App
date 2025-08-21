package com.giraffe.presentation.authentication.screens.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.authentication.R
import com.giraffe.presentation.authentication.screens.onboarding.composable.ImagePager
import com.giraffe.presentation.authentication.screens.onboarding.composable.OnBoardingFooter
import com.giraffe.presentation.authentication.utils.EffectListener
import com.giraffe.presentation.authentication.utils.showToast
import com.giraffe.presentation.authentication.utils.toStringResource

@Composable
fun OnBoardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
) {

    val context = LocalContext.current

    EffectListener(events = viewModel.effect) {

        when (it) {
            is OnboardingEffect.NavigateToLogin -> navigateToLoginScreen()
            is OnboardingEffect.ShowError ->
                context.showToast(context.getString(it.throwable.toStringResource()))
        }

    }


    OnBoardingContent(
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
    val imagePagerState = rememberPagerState(pageCount = { pages.size })
    val textPagerState = rememberPagerState(pageCount = { pages.size })
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Theme.color.background.screen),
        verticalArrangement = Arrangement.Bottom
    ) {
        ImagePager(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 69.dp),
            pagerState = imagePagerState,
            images = pages.map { it.imageRes },
        )

        OnBoardingFooter(
            imagePagerState = imagePagerState,
            textPagerState = textPagerState,
            pages = pages,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            interaction = interaction
        )
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

@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun OnBoardingContentPreview() {
    OnBoardingContent(
        pages = listOf(
            OnBoardingPage(
                imageRes = R.drawable.onboard1,
                title = "Welcome to Your Movie Universe!",
                subtitle = "Discover, track, and rate your favorite movies & series"
            ),
            OnBoardingPage(
                imageRes = R.drawable.onboard2,
                title = "Track Everything",
                subtitle = "Your watchlist, your ratings, all in one place"
            ),
            OnBoardingPage(
                imageRes = R.drawable.onboard3,
                title = "Personalized Recommendations",
                subtitle = "Answer fun questions to get handpicked recommendations"
            )
        ),
        interaction = object : OnboardingInteractionListener {
            override fun markOnboardingComplete() {

            }
        }
    )
}
