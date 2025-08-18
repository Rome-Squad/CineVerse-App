package com.giraffe.presentation.details.screens.castDetails

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoSection
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.components.MainCastDetails
import com.giraffe.presentation.details.components.PosterListSection
import com.giraffe.presentation.details.components.gallery.GallerySection
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun CastDetailsScreen(
    navigateToCastCredit: (castID: Int, actorName: String) -> Unit,
    navigateToGallery: (String, Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    onBackButtonClick: () -> Unit,
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

            is CastDetailsEffect.NavigateToGallery ->
                navigateToGallery(it.actorName, it.personId)

            is CastDetailsEffect.NavigateToCastCredit -> navigateToCastCredit(
                it.castID,
                it.actorName
            )

            is CastDetailsEffect.NavigateToMovieDetails ->
                navigateToMovieDetails(it.movieId)

            is CastDetailsEffect.NavigateToSeriesDetails ->
                navigateToSeriesDetails(it.seriesId)

            is CastDetailsEffect.NavigateBack -> onBackButtonClick()

            is CastDetailsEffect.ShowError -> {
                context.showToast(it.exception.toStringResource())
            }
        }
    }

    CastDetailsContent(
        state = state,
        interaction = castDetailsViewModel
    )
}

@Suppress("SameReturnValue")

@Composable
fun CastDetailsContent(
    state: CastDetailsUiState,
    interaction: CastDetailsInteractionListener,
    modifier: Modifier = Modifier,
) {
    val padding16 = 16.dp
    val bottomSpacingHeight = 90
    val innerColumnSpacing = 18.dp

    val scrollState = rememberLazyListState()

    val animationProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var lastDelta by remember { mutableIntStateOf(0) }
    var flingJob by remember { mutableStateOf<Job?>(null) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()

                if (
                    (scrollState.firstVisibleItemIndex != 0 || scrollState.firstVisibleItemScrollOffset != 0)
                    && delta > 0
                ) {
                    return Offset.Zero
                }

                if (source == NestedScrollSource.UserInput) {
                    flingJob?.cancel()
                    flingJob = null
                }

                val current = animationProgress.value
                val change = -delta / 40f
                val newProgress = (current + change).coerceIn(0f, 1f)

                scope.launch {
                    animationProgress.snapTo(newProgress)
                }

                if (delta != 0) lastDelta = delta

                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val target = if (lastDelta < 0) 1f else 0f
                flingJob = scope.launch {
                    animationProgress.animateTo(
                        target,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )
                }
                return Velocity.Zero
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
            .nestedScroll(nestedScrollConnection)
    ) {
        if (state.isNoInternet) {
            NoInternetScreen(
                modifier = Modifier
                    .align(Alignment.Center),
                onRetryClick = interaction::onRetryClick
            )
        }

        if (state.isLoading) {
            Progress(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if (!(state.isLoading || state.isNoInternet)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.background.screen)
                    .systemBarsPadding(),

                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(innerColumnSpacing)
            ) {
                stickyHeader {
                    Box(Modifier.background(Theme.color.background.screen)) {
                        AppBar(
                            showBackButton = true,
                            hasBackground = false,
                            onBackButtonClick = interaction::onBackClick,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        MainCastDetails(
                            actorName = state.actorName,
                            actorBirthday = state.actorBirth,
                            actorPlaceOfBirth = state.actorPlace,
                            socialMediaUiList = state.socialMediaUiList,
                            onLinkClick = interaction::onSocialMediaLinkClick,
                            actorImageUrl = state.actorImageUrl,
                            animationProgress = animationProgress.value,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 72.dp - 72.dp * animationProgress.value)
                        )

                        AnimatedVisibility(
                            visible = animationProgress.value == 1f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .background(Theme.color.stroke.primary)
                                    .align(Alignment.BottomStart)
                            )
                        }
                    }
                }

                item {
                    PosterListSection(
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
                            interaction.onShowMoreCastCreditsTextClick(
                                state.actorId,
                                state.actorName
                            )
                        }
                    )
                }

                item {
                    GallerySection(
                        modifier = Modifier
                            .height(314.dp)
                            .fillMaxWidth()
                            .padding(horizontal = padding16),
                        imageUrls = state.actorGalleryImageUrls,
                        onShowMoreClick = interaction::onShowMoreGalleryTextClick
                    )
                }

                item {
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

    }

}