package com.giraffe.details.screens.castDetails

import android.content.Intent
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.MainDetails
import com.giraffe.details.components.MainDetailsHeader
import com.giraffe.details.components.gallery.GallerySection
import com.giraffe.details.screens.gallery.navigateToGallery
import com.giraffe.details.utils.EventListener
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CastDetailsScreen(
    personId: Int?,
    navController: NavController,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    castDetailsViewModel: CastDetailsViewModel = koinViewModel(parameters = { parametersOf(personId) })
) {
    val state by castDetailsViewModel.state.collectAsState()
    val context = LocalContext.current
    EventListener(
        events = castDetailsViewModel.effect,
    ) {
        when (it) {
            is CastDetailsEffect.Error -> {}
            is CastDetailsEffect.OpenUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, it.url.toUri())
                context.startActivity(intent)
            }

            is CastDetailsEffect.NavigateToMovies -> {}
            is CastDetailsEffect.NavigateToGallery -> {
                navController.navigateToGallery(it.actorName, it.imageUrls)
            }
        }
    }
    if (state.isLoading) {
        LoadingView()
    } else {
        CastDetailsContent(
            state = state,
            interaction = castDetailsViewModel,
            onBackArrowClick = onBackButtonClick,
            modifier = modifier,
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
    onBackArrowClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                    actorImageUrl = state.actorImageUrl,
                    actorName = state.actorName,
                    actorBirthday = state.actorBirth,
                    actorPlaceOfBirth = state.actorPlace,
                    onYoutubeClick = interaction::onActorYoutubeLinkClicked,
                    onFacebookClick = interaction::onActorFacebookLinkClicked,
                    onInstagramClick = interaction::onActorInstagramLinkClicked,
                    hasYoutube = state.actorYouTubeLink.isNotBlank(),
                    hasFacebook = state.actorFacebookLink.isNotBlank(),
                    hasInstagram = state.actorInstagramLink.isNotBlank()
                )
                AppBar(
                    showBackButton = true,
                    hasBackground = false,
                    onBackButtonClick = onBackArrowClick,
                    modifier = Modifier.padding(horizontal = padding16)
                )
            }
        }
        item {
            MoviesListSection(
                title = stringResource(R.string.best_of) + " " + state.actorName,
                endText = stringResource(R.string.show_more),
                movies = state.posters,
                onClickPoster = {},
                onClickEndText = { }
            )
        }
        item {
            GallerySection(
                modifier = Modifier
                    .height(314.dp)
                    .fillMaxWidth()
                    .padding(horizontal = padding16),
                imageUrls = state.actorGalleryImageUrls,
                onShowMoreClick = interaction::navigateToActorGalleryScreen
            )
        }
        item {
            InfoSection(
                modifier = Modifier.padding(horizontal = padding16),
                title = stringResource(R.string.biography),
                description = state.biographyInfo
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
    hasYoutube: Boolean = false,
    hasFacebook: Boolean = false,
    hasInstagram: Boolean = false,
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
                        actorImageUrl = actorImageUrl,
                        actorName = actorName,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }

                false -> {
                    MainDetails(
                        actorName = actorName,
                        actorBirthday = actorBirthday,
                        actorPlaceOfBirth = actorPlaceOfBirth,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent,
                        onYoutubeClick = onYoutubeClick,
                        onFacebookClick = onFacebookClick,
                        onInstagramClick = onInstagramClick,
                        actorImageUrl = actorImageUrl,
                        modifier = Modifier
                            .padding(horizontal = horizontalPadding)
                            .padding(top = topPadding),
                        hasYoutube = hasYoutube,
                        hasFacebook = hasFacebook,
                        hasInstagram = hasInstagram
                    )
                }
            }
        }
    }
}