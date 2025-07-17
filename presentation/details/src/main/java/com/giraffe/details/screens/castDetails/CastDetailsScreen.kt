package com.giraffe.details.screens.castDetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.MainDetails
import com.giraffe.details.components.MainDetailsHeader
import com.giraffe.details.components.gallery.GallerySection
import com.giraffe.details.utils.EventListener
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CastDetailsScreen(
    personId: Int,
    modifier: Modifier = Modifier,
    castDetailsViewModel: CastDetailsViewModel = koinViewModel(parameters = { parametersOf(personId) })
) {
    val state by castDetailsViewModel.state.collectAsState()
    EventListener(
        events = castDetailsViewModel.effect,
    ) {
        when (it) {
            is CastDetailsEffect.Error -> TODO()
        }
    }
    if (state.isLoading) {
        LoadingView()
    } else {
        CastDetailsContent(
            state = state,
            interaction = castDetailsViewModel,
        )
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .wrapContentSize()
    ) {
        Progress(
            size = 40.dp,
            strokeWidth = 4.dp
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CastDetailsContent(
    state: CastDetailsUiState,
    interaction: CastDetailsInteractionListener,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val padding16 = 16.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        Box {
            MainDetailsAnimatedContent(
                isScrolled = scrollState.value > 0,
                actorImageUrl = state.actorImageUrl,
                actorName = state.actorName,
                actorBirthday = state.actorBirth,
                actorPlaceOfBirth = state.actorPlace,
                onYoutubeClick = interaction::onActorYoutubeLinkClicked,
                onFacebookClick = interaction::onActorFacebookLinkClicked,
                onInstagramClick = interaction::onActorInstagramLinkClicked,
            )
            AppBar(
                showBackButton = true,
                hasBackground = false,
                modifier = Modifier.padding(horizontal = padding16)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(horizontal = padding16)
                .verticalScroll(scrollState)
        ) {
            MoviesListSection(
                title = stringResource(R.string.best_of) + " " + state.actorName,
                endText = stringResource(R.string.show_more),
                movies = state.posters,
                onClickPoster = interaction::onMovieClicked,
                onClickEndText = interaction::navigateToMoviesListScreen,
                modifier = Modifier.padding(top = 24.dp)
            )
            GallerySection(
                modifier = Modifier
                    .height(314.dp)
                    .fillMaxWidth()
                    .padding(horizontal = padding16),
                imageUrls = state.actorGalleryImageUrls,
                onShowMoreClick = interaction::navigateToActorGalleryScreen
            )
            InfoSection(
                title = stringResource(R.string.biography),
                description = state.biographyInfo,
                modifier = Modifier
                    .padding(horizontal = padding16)
                    .padding(bottom = padding16)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun MainDetailsAnimatedContent(
    isScrolled: Boolean,
    actorName: String,
    actorBirthday: String,
    actorPlaceOfBirth: String,
    actorImageUrl: String?,
    onYoutubeClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onInstagramClick: () -> Unit,
) {
    val horizontalPadding = 16.dp
    val duration = 400
    val topPadding by animateDpAsState(
        if (isScrolled) 56.dp else 72.dp
    )
    SharedTransitionLayout {
        AnimatedContent(
            targetState = isScrolled,
            transitionSpec = {
                fadeIn(animationSpec = tween(duration, easing = EaseIn))
                    .togetherWith(
                        fadeOut(
                            animationSpec = tween(duration, easing = EaseOut)
                        )
                    )
            },
            label = "Main Details Animated Content"
        ) { targetState ->
            when (targetState) {
                true -> {
                    MainDetailsHeader(
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                        actorImageUrl = actorImageUrl,
                        actorName = actorName,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }

                false -> {
                    MainDetails(
                        modifier = Modifier
                            .padding(horizontal = horizontalPadding)
                            .padding(top = topPadding),
                        actorImageUrl = actorImageUrl,
                        actorName = actorName,
                        actorBirthday = actorBirthday,
                        actorPlaceOfBirth = actorPlaceOfBirth,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onYoutubeClick = onYoutubeClick,
                        onFacebookClick = onFacebookClick,
                        onInstagramClick = onInstagramClick
                    )
                }
            }
        }
    }
}