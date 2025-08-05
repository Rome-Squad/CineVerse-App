package com.giraffe.details.screens.castDetails

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
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
//    if (state.isLoading) {
//        LoadingView()
//    } else {
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

        if (state.isLoading) LoadingView()

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
//    }
}

@Composable
fun CastDetailsContent(
    state: CastDetailsUiState,
    interaction: CastDetailsInteractionListener,
    onBackArrowClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val padding16 = 16.dp
    val scrollState = rememberLazyListState()
    var bottomSpacingHeight by remember { mutableIntStateOf(90) }
    val innerColumnSpacing = 18.dp
    var imageWidth by remember { mutableIntStateOf(64) }
    var imageHeight by remember { mutableIntStateOf(80) }
    var consumedX by remember { mutableIntStateOf(0) }
    var consumedY by remember { mutableIntStateOf(0) }
    var animationProgress by remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                if (
                    (scrollState.firstVisibleItemIndex != 0 || scrollState.firstVisibleItemScrollOffset != 0)
                    && delta > 0
                ) {
                    return Offset(consumedX.toFloat(), consumedY.toFloat())
                }
                val newImageWidth = imageWidth + delta
                val previousImageWidth = imageWidth
                imageWidth = newImageWidth.coerceIn(40, 64)
                val newImageHeight = imageHeight + delta
                val previousImageHeight = imageHeight
                imageHeight = newImageHeight.coerceIn(40, 80)
                animationProgress = 1f - (imageHeight - 40) / 40f
                consumedX = imageWidth - previousImageWidth
                consumedY = imageHeight - previousImageHeight
                return Offset(consumedX.toFloat(), consumedY.toFloat())
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
        LazyColumn(
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(innerColumnSpacing)
        ) {
            stickyHeader {
                Box(Modifier.background(Theme.color.background.screen)) {
                    AppBar(
                        showBackButton = true,
                        hasBackground = false,
                        onBackButtonClick = onBackArrowClick,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    MainDetailsAnimatedContent(
                        actorImageUrl = state.actorImageUrl,
                        actorName = state.actorName,
                        actorBirthday = state.actorBirth,
                        actorPlaceOfBirth = state.actorPlace,
                        socialMediaUiList = state.socialMediaUiList,
                        onLinkClick = interaction::navigateToActorMediaLink,
                        animationProgress = animationProgress
                    )
                }
            }

            item {
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

@Composable
private fun MainDetailsAnimatedContent(
    actorName: String,
    actorBirthday: String,
    actorPlaceOfBirth: String,
    actorImageUrl: String?,
    socialMediaUiList: List<SocialMediaUi>,
    onLinkClick: (String) -> Unit,
    animationProgress: Float
) {
    MainDetails(
        actorName = actorName,
        actorBirthday = actorBirthday,
        actorPlaceOfBirth = actorPlaceOfBirth,
        socialMediaUiList = socialMediaUiList,
        onLinkClick = onLinkClick,
        actorImageUrl = actorImageUrl,
        animationProgress = animationProgress,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 72.dp - 72.dp * animationProgress),
    )
}