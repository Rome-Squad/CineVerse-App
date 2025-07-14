package com.giraffe.details.screens.castDetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.GallerySection
import com.giraffe.details.components.MainDetails
import com.giraffe.details.components.MainDetailsHeader
import com.giraffe.details.utils.imageSourceToPainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun CastDetailsScreen(
    castDetailsViewModel: CastDetailsViewModel = koinViewModel()
) {
    val state by castDetailsViewModel.state.collectAsState()
    CastDetailsContent(
        state = state,
        onYoutubeClick = castDetailsViewModel::onActorYoutubeLinkClicked,
        onFacebookClick = castDetailsViewModel::onActorFacebookLinkClicked,
        onInstagramClick = castDetailsViewModel::onActorInstagramLinkClicked,
        onMoviePosterClick = castDetailsViewModel::onMovieClicked,
        onShowMoreMoviesClick = castDetailsViewModel::navigateToMoviesListScreen,
        onShowMoreActorGalleryClick = castDetailsViewModel::navigateToActorGalleryScreen,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CastDetailsContent(
    state: CastDetailsUiState,
    modifier: Modifier = Modifier,
    onYoutubeClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onInstagramClick: () -> Unit,
    onMoviePosterClick: (movieId: Int) -> Unit,
    onShowMoreMoviesClick: () -> Unit,
    onShowMoreActorGalleryClick: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 5
        }
    }
    val padding16 = 16.dp
    LazyColumn(
        state = scrollState,
        contentPadding = PaddingValues(bottom = padding16),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        stickyHeader {
            Box {
                MainDetailsAnimatedContent(
                    isScrolled = isScrolled,
                    actorImage = state.actorImage.imageSourceToPainter(),
                    actorName = state.actorName,
                    actorBirthday = state.actorBirth,
                    actorPlaceOfBirth = state.actorPlace,
                    onYoutubeClick = onYoutubeClick,
                    onFacebookClick = onFacebookClick,
                    onInstagramClick = onInstagramClick,
                )
                AppBar(
                    showBackButton = true,
                    hasBackground = false,
                    modifier = Modifier.padding(horizontal = padding16)
                )
            }
        }
        item {
            MoviesListSection(
                title = state.titleMoviesSection,
                endText = stringResource(R.string.show_more),
                movies = state.posters,
                onClickPoster = onMoviePosterClick,
                onClickEndText = onShowMoreMoviesClick
            )
        }
        item {
            GallerySection(
                modifier = Modifier
                    .height(314.dp)
                    .fillMaxWidth()
                    .padding(horizontal = padding16),
                images = state.actorGalleryImages,
                onShowMoreClick = onShowMoreActorGalleryClick
            )
        }
        item {
            InfoSection(
                modifier = Modifier.padding(horizontal = padding16),
                title = state.titleInfoSection,
                description = state.descriptionInfoSection
            )
        }
    }

}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun MainDetailsAnimatedContent(
    isScrolled: Boolean,
    actorImage: Painter,
    actorName: String,
    actorBirthday: String,
    actorPlaceOfBirth: String,
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
                fadeIn(
                    animationSpec = tween(duration)
                ) togetherWith fadeOut(animationSpec = tween(duration))
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {
                true -> {
                    MainDetailsHeader(
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                        actorImage = actorImage,
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
                        actorImage = actorImage,
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