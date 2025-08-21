package com.giraffe.presentation.home.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.HorizontalDivider
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.R
import com.giraffe.presentation.home.components.AdvertisementSection
import com.giraffe.presentation.home.components.Carousel
import com.giraffe.presentation.home.components.CollectionListSection
import com.giraffe.presentation.home.components.ListSection
import com.giraffe.presentation.home.components.YourCollectionsSections
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.utils.EffectListener
import com.giraffe.presentation.home.utils.showToast
import com.giraffe.presentation.home.utils.toStringRes

@Composable
fun HomeScreen(
    navigateToCategoryMediaSection: (CategoryMediaSectionType) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToYourCollection: () -> Unit,
    navigateToExploreScreen: () -> Unit,
    navigateToMatchScreen: () -> Unit,
    navigateToCollection: (collectionId: Int, collectionName: String) -> Unit,
    navigateToFeaturedCollection: (collectionId: Int, collectionTitle: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    EffectListener(viewModel.effect) { effect ->
        when (effect) {
            is HomeEffect.NavigateToMovieDetails -> navigateToMoviesDetailsScreen(effect.movieId)
            is HomeEffect.NavigateToSeriesDetails -> navigateToSeriesDetailsScreen(effect.seriesId)
            is HomeEffect.NavigateToExploreScreen -> navigateToExploreScreen()
            is HomeEffect.NavigateToFeaturedCollection -> navigateToFeaturedCollection(
                effect.collectionId,
                effect.collectionTitle
            )

            is HomeEffect.NavigateToCollection -> navigateToCollection(
                effect.collectionId,
                effect.collectionName
            )

            is HomeEffect.NavigateToYourCollection -> navigateToYourCollection()
            is HomeEffect.NavigateToMatchScreen -> navigateToMatchScreen()
            is HomeEffect.NavigateToCategoryMediaSection -> navigateToCategoryMediaSection(effect.sectionType)

            is HomeEffect.ShowError -> context.showToast(effect.error.toStringRes())
        }
    }

    HomeContent(
        state = state,
        interactionListener = viewModel
    )
}

@Composable
fun HomeContent(
    state: HomeScreenState,
    interactionListener: HomeInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .statusBarsPadding()
    ) {
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.background.screen)
                .padding(start = 16.dp),
            image = painterResource(Theme.icons.colored.logo),
            caption = if (state.userName.isNotBlank()) stringResource(R.string.welcome) else null,
            title = state.userName.ifBlank { stringResource(com.giraffe.designsystem.R.string.home) }
        )
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Carousel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                popularMediaItems = state.popularity,
                isLoading = state.isLoadingPopularity,
                onClickItem = interactionListener::onMediaClicked,
                contentPreference = state.contentPreference
            )
            ListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                title = stringResource(R.string.recently_released),
                endText = stringResource(R.string.show_more),
                posters = state.recentlyReleased,
                isLoading = state.isLoadingRecentlyReleased,
                hasError = state.hasRecentlyReleasedError,
                onRetry = interactionListener::getRecentlyReleased,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onSeeMoreClicked(
                        sectionType = CategoryMediaSectionType.RECENTLY_RELEASED
                    )
                },
                contentPreference = state.contentPreference
            )
            AdvertisementSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                title = stringResource(R.string.what_should_i_watch),
                cardTitle = stringResource(R.string.let_us_choose_for_you),
                caption =
                    stringResource(R.string.we_ll_help_you_skip_the_scroll_and_go_straight_to_the_good_stuff),
                onCardClick = interactionListener::onMatchSectionClicked,
            )
            ListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                title = stringResource(R.string.upcoming_movies),
                endText = stringResource(R.string.show_more),
                posters = state.upcomingMovies,
                isLoading = state.isLoadingUpcoming,
                hasError = state.hasUpcomingError,
                onRetry = interactionListener::getUpcoming,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onSeeMoreClicked(
                        sectionType = CategoryMediaSectionType.UPCOMING_MOVIES
                    )
                },
                contentPreference = state.contentPreference

            )
            AnimatedVisibility(state.isLoadingMatchesYourVibe || state.hasMatchesYourVibeError || state.matchVibes.isNotEmpty()) {
                ListSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    title = stringResource(R.string.matches_your_vibe),
                    endText = stringResource(R.string.show_more),
                    posters = state.matchVibes,
                    isLoading = state.isLoadingMatchesYourVibe,
                    hasError = state.hasMatchesYourVibeError,
                    onRetry = interactionListener::getMatchesYourVibe,
                    onClickItem = interactionListener::onMediaClicked,
                    onClickEndText = {
                        interactionListener.onSeeMoreClicked(
                            sectionType = CategoryMediaSectionType.MATCHES_YOUR_VIBES
                        )
                    },
                    contentPreference = state.contentPreference
                )
            }
            CollectionListSection(
                modifier = Modifier.padding(vertical = 16.dp),
                collectionItems = state.featuredCollections,
                onCollectionItemClick = interactionListener::onFeaturesCollectionClicked
            )
            ListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                title = stringResource(R.string.top_rated_tv_shows),
                endText = stringResource(R.string.show_more),
                posters = state.topRated,
                isLoading = state.isLoadingTopRated,
                hasError = state.hasTopRatedError,
                onRetry = interactionListener::getTopRated,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onSeeMoreClicked(
                        sectionType = CategoryMediaSectionType.TOP_RATED_TV_SHOWS
                    )
                },
                contentPreference = state.contentPreference
            )
            AnimatedVisibility(state.isLoadingRecentlyViewed || state.hasRecentlyViewedError || state.recentlyViewed.isNotEmpty()) {
                ListSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    endText = stringResource(R.string.show_more),
                    title = stringResource(R.string.you_recent_viewed),
                    posters = state.recentlyViewed,
                    isLoading = state.isLoadingRecentlyViewed,
                    hasError = state.hasRecentlyViewedError,
                    onRetry = interactionListener::getRecentlyViewed,
                    onClickItem = interactionListener::onMediaClicked,
                    onClickEndText = {
                        interactionListener.onSeeMoreClicked(
                            sectionType = CategoryMediaSectionType.RECENTLY_VIEWED
                        )
                    },
                    contentPreference = state.contentPreference
                )
            }
            AnimatedVisibility(state.yourCollections.isNotEmpty()) {
                YourCollectionsSections(
                    modifier = Modifier.padding(vertical = 16.dp),
                    collectionItems = state.yourCollections,
                    onShowMoreClick = interactionListener::onYourCollectionClicked,
                    onCollectionClick = interactionListener::onCollectionClick
                )
            }
            AdvertisementSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 50.dp, top = 16.dp),
                title = stringResource(R.string.need_more_to_watch),
                cardTitle = stringResource(R.string.browse_everything),
                caption = stringResource(R.string.explore_all_movies_and_series),
                onCardClick = interactionListener::onExploreSectionClicked
            )
        }
    }
}