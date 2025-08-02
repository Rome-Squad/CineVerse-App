package com.giraffe.details.screens.castDetails

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.PosterListSection
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.LoadingView
import com.giraffe.details.components.MainDetails
import com.giraffe.details.components.MainDetailsHeader
import com.giraffe.details.components.gallery.GallerySection
import com.giraffe.details.screens.castDetails.state.CastDetailsUiState
import com.giraffe.details.screens.castDetails.state.SocialMediaUi
import com.giraffe.details.utils.EventListener

@Composable
fun CastDetailsScreen(
    navigateToCastCredit: (castID: Int, actorName: String) -> Unit,
    navigateToGallery: (String, Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    castDetailsViewModel: CastDetailsViewModel = hiltViewModel()
) {
    val state by castDetailsViewModel.state.collectAsState()
    val context = LocalContext.current
    EventListener(
        events = castDetailsViewModel.effect,
    ) {
        when (it) {
            is CastDetailsEffect.OpenUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, it.url.toUri())
                context.startActivity(intent)
            }

            is CastDetailsEffect.NavigateToGallery -> navigateToGallery(it.actorName, it.personId)
            is CastDetailsEffect.NavigateToCastCredit -> navigateToCastCredit(
                it.castID,
                it.actorName
            )

            is CastDetailsEffect.NavigateToMovieDetails -> navigateToMovieDetails(it.movieId)
            is CastDetailsEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(it.seriesId)
        }
    }
    if (state.isLoading) {
        LoadingView()
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.color.background.screen)
                .wrapContentSize()
        ) {
            CastDetailsContent(
                state = state,
                interaction = castDetailsViewModel,
                onBackArrowClick = onBackButtonClick,
                modifier = modifier,
            )
            AnimatedVisibility(
                visible = !state.errorMessage.isNullOrBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .background(Theme.color.shade.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = state.errorMessage
                        ?: stringResource(R.string.unknown_error),
                    color = Theme.color.additional.primary.red,
                    textAlign = TextAlign.Center,
                    style = Theme.textStyle.label.md.regular
                )
            }
        }
    }
}

@Composable
fun CastDetailsContent(
    state: CastDetailsUiState,
    interaction: CastDetailsInteractionListener,
    onBackArrowClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val padding16 = 16.dp
    val scrollState = rememberScrollState()
    val windowHeight = LocalWindowInfo.current.containerSize.height
    var bottomSpacingHeight by remember { mutableIntStateOf(90) }
    val isScrolled by remember {
        derivedStateOf {
            scrollState.value > 0
        }
    }
    val innerColumnSpacing = 18.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        Box {
            MainDetailsAnimatedContent(
                isScrolled = isScrolled,
                actorImageUrl = state.actorImageUrl,
                actorName = state.actorName,
                actorBirthday = state.actorBirth,
                actorPlaceOfBirth = state.actorPlace,
                socialMediaUiList = state.socialMediaUiList,
                onLinkClick = interaction::navigateToActorMediaLink
            )
            AppBar(
                showBackButton = true,
                hasBackground = false,
                onBackButtonClick = onBackArrowClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(innerColumnSpacing),
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .onGloballyPositioned { coordinates ->
                    val contentHeight = coordinates.size.height
                    bottomSpacingHeight =
                        if (contentHeight > windowHeight + bottomSpacingHeight) 16 else 115
                }
        ) {
            PosterListSection(
                modifier = Modifier.padding(top = innerColumnSpacing),
                title = stringResource(R.string.best_of) + " " + state.actorName,
                endText = stringResource(R.string.show_more),
                posters = state.posters,
                onClickPoster = {
                    interaction.onPosterClick(
                        mediaId = it.id,
                        mediaType = it.mediaTypeOfPoster.toString()
                    )
                },
                onClickEndText = {
                    interaction.navigateToCastCreditScreen(
                        state.actorId,
                        state.actorName
                    )
                }
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
                modifier = Modifier
                    .padding(horizontal = padding16)
                    .padding(bottom = bottomSpacingHeight.dp),
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
    socialMediaUiList: List<SocialMediaUi>,
    onLinkClick: (String) -> Unit,
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
                        socialMediaUiList = socialMediaUiList,
                        onLinkClick = onLinkClick,
                        actorImageUrl = actorImageUrl,
                        modifier = Modifier
                            .padding(horizontal = horizontalPadding)
                            .padding(top = topPadding),
                    )
                }
            }
        }
    }
}