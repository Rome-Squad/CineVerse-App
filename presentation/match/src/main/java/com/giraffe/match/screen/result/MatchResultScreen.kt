package com.giraffe.match.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.button_type.PrimaryButton
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.components.HeroCarousel
import com.giraffe.match.components.LoginBottomSheet
import com.giraffe.match.components.collectionBottomSheet.MovieCollectionsBottomSheet
import com.giraffe.match.composable.BaseScreenWithStates
import com.giraffe.match.utils.EffectListener
import com.giraffe.match.utils.showToast
import com.giraffe.match.utils.toStringResource
import com.giraffe.presentation.match.R

@Composable
fun MatchResultScreen(
    navigateBack: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToYouTubePlayer: (String) -> Unit,
    navigateToLoginScreen: () -> Unit,
    viewModel: MatchResultViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    EffectListener(viewModel.effect) { effect ->
        when (effect) {
            is MatchResultScreenEffect.ShowError ->
                context.showToast(effect.error.toStringResource())

            MatchResultScreenEffect.NavigateBack ->
                navigateBack()

            MatchResultScreenEffect.NavigateToLogin ->
                navigateToLoginScreen()

            is MatchResultScreenEffect.NavigateToMovieDetails ->
                navigateToMoviesDetailsScreen(effect.movieId)

            is MatchResultScreenEffect.NavigateToSeriesDetails ->
                navigateToSeriesDetailsScreen(effect.seriesId)

            is MatchResultScreenEffect.NavigateToYouTubePlayer ->
                navigateToYouTubePlayer(effect.youtubeVideoId)
        }
    }


    BaseScreenWithStates(
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onRetryClick = viewModel::onRetryClick,
    ) {
        Box(Modifier.fillMaxSize()) {
            MatchResultContent(
                state = state,
                onPageChanged = viewModel::onCarouselPageChanged,
                navigateBack = viewModel::onBackClick,
                navigateToMoviesDetailsScreen = viewModel::navigateToMoviesDetailsScreen,
                navigateToSeriesDetailsScreen = viewModel::navigateToSeriesDetailsScreen,
                navigateToYouTubePlayer = viewModel::navigateToYouTubePlayer,
                onAddToCollection = viewModel::onAddToCollection,
            )


            if (state.collectionBottomSheet != null) {
                MovieCollectionsBottomSheet(
                    isVisible = true,
                    onDismiss = viewModel::onCollectionBottomSheetDismiss,
                    targetState = state.collectionBottomSheet,
                    collections = state.collections,
                    onNewCollectionClick = viewModel::onCreateCollectionButtonClick,
                    onCollectionClick = viewModel::onCollectionClick,
                    onCreateCollectionButtonClick = viewModel::onCreateCollectionButtonClick,
                    newCollectionName = state.newCollectionName,
                    onNewCollectionNameChange = viewModel::onNewCollectionNameChange,
                    onConfirmCreateNewCollectionClick = viewModel::onConfirmCreateNewCollectionClick,
                    onCancelCreateNewCollectionClick = viewModel::onCancelCreateNewCollectionClick,
                    modifier = Modifier.padding(12.dp)
                )
            }


            if (state.isVisibleLoginBottomSheet) {
                LoginBottomSheet(
                    isVisible = true,
                    onLogInClick = viewModel::onLoginButtonClick,
                    onDismiss = viewModel::onDismissLoginBottomSheet
                )
            }
        }
    }
}

@Composable
private fun MatchResultContent(
    state: MatchResultScreenState,
    navigateBack: () -> Unit,
    onPageChanged: (Int) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToYouTubePlayer: (String) -> Unit,
    onAddToCollection: (Int, MediaType) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = state.currentCarouselPage

    Column(
        modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Theme.color.background.screen)
    ) {
        AppBar(
            showBackButton = true,
            title = stringResource(R.string.here_s_your_match_list),
            onBackButtonClick = navigateBack,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        if (state.isEmptyResults) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                MessageInfoBox(
                    title = stringResource(R.string.no_results_title),
                    caption = stringResource(R.string.no_results_caption),
                    icon = painterResource(Theme.icons.dueTone.linkMinimalistic),
                    iconTintColor = Theme.color.brand.primary,
                    buttonBackgroundColor = Theme.color.brand.primary,
                    iconBackgroundColor = Theme.color.brand.tertiary,
                    isSecondaryButtonVisible = false,
                    titlePrimaryButton = stringResource(R.string.try_again),
                    onClickPrimaryButton = navigateBack,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeroCarousel(
                    items = state.matchItems.map { it.posterUrl },
                    onPageChanged = { newIndex -> onPageChanged(newIndex) },
                    onItemClick = { clickedIndex ->
                        val clickedItem = state.matchItems.getOrNull(clickedIndex)
                        clickedItem?.let {
                            when (it.mediaType) {
                                MediaType.MOVIE -> navigateToMoviesDetailsScreen(it.id)
                                MediaType.SERIES -> navigateToSeriesDetailsScreen(it.id)
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                val match = state.matchItems.getOrNull(selectedIndex)
                match?.let {
                    MainMovieOrSeriesDetails(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        type = it.mediaType,
                        name = it.title,
                        genres = it.genres,
                        rating = it.rating,
                        duration = it.duration,
                        releaseDate = it.releaseDate,
                        isPlayButtonEnabled = it.youtubeVideoId.isNotBlank(),
                        onClickPlay = { navigateToYouTubePlayer(it.youtubeVideoId) },
                        onClickAdd = { onAddToCollection(it.id, it.mediaType) }
                    )
                }

                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    text = stringResource(R.string.view_details),
                ) {
                    match?.let {
                        when (it.mediaType) {
                            MediaType.MOVIE -> navigateToMoviesDetailsScreen(it.id)
                            MediaType.SERIES -> navigateToSeriesDetailsScreen(it.id)
                        }
                    }
                }
            }
        }
    }
}



