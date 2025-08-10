package com.giraffe.presentation.home.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.R
import com.giraffe.presentation.home.components.AdvertisementSection
import com.giraffe.presentation.home.components.BaseScreenWithStates
import com.giraffe.presentation.home.components.Carousel
import com.giraffe.presentation.home.components.CollectionListSection
import com.giraffe.presentation.home.components.HorizontalDivider
import com.giraffe.presentation.home.components.ListSection
import com.giraffe.presentation.home.components.TopAppBar
import com.giraffe.presentation.home.components.YourCollectionsSections
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.PopularMediaUi
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType
import com.giraffe.presentation.home.utils.EffectListener
import com.giraffe.presentation.home.utils.showToast
import com.giraffe.presentation.home.utils.toStringRes

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToShowMoreScreen: (ShowMoreSectionType) -> Unit,
    navigateToFeaturedCollection: (collectionId: Int, collectionTitle: String) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToYourCollection: () -> Unit,
    navigateToExploreScreen: () -> Unit,
    navigateToMatchScreen: () -> Unit,
    navigateToCollection: (collectionId: Int, collectionName: String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    EffectListener(viewModel.effect) { effect ->
        when (effect) {
            is HomeEffect.NavigateToMovieDetails -> navigateToMoviesDetailsScreen(effect.movieId)
            is HomeEffect.NavigateToSeriesDetails -> navigateToSeriesDetailsScreen(effect.seriesId)
            is HomeEffect.NavigateToExploreScreen -> navigateToExploreScreen()
            is HomeEffect.NavigateToFeaturedCollection -> {
                navigateToFeaturedCollection(effect.collectionId, effect.collectionTitle)
            }

            is HomeEffect.NavigateToCollection -> {
                navigateToCollection(effect.collectionId, effect.collectionName)
            }

            is HomeEffect.NavigateToYourCollection -> navigateToYourCollection()
            is HomeEffect.NavigateToMatchScreen -> navigateToMatchScreen()
            is HomeEffect.NavigateToShowMore -> {
                navigateToShowMoreScreen(effect.sectionType)
            }

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
    var topAppBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    BaseScreenWithStates(
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.background.screen)
                .statusBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = topAppBarHeight)
            ) {
                if (state.popularity.isNotEmpty()) {
                    Carousel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        movieCards = state.popularity,
                        onClickItem = interactionListener::onMediaClicked
                    )
                }

                if (state.recentlyReleased.isNotEmpty()) {
                    ListSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        title = stringResource(R.string.recently_released),
                        endText = stringResource(R.string.show_more),
                        uiModels = state.recentlyReleased,
                        onClickItem = interactionListener::onMediaClicked,
                        onClickEndText = {
                            interactionListener.onSeeAllRecentlyReleasedClicked(
                                sectionType = ShowMoreSectionType.RECENTLY_RELEASED
                            )
                        }
                    )
                }

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

                if (state.upcomingMovies.isNotEmpty()) {
                    ListSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        title = stringResource(R.string.upcoming_movies),
                        endText = stringResource(R.string.show_more),
                        uiModels = state.upcomingMovies,
                        onClickItem = interactionListener::onMediaClicked,
                        onClickEndText = {
                            interactionListener.onSeeAllUpcomingClicked(
                                sectionType = ShowMoreSectionType.UPCOMING_MOVIES
                            )
                        }
                    )
                }
                if (state.matchVibes.isNotEmpty()) {
                    ListSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        title = stringResource(R.string.matches_your_vibe),
                        endText = stringResource(R.string.show_more),
                        uiModels = state.matchVibes,
                        onClickItem = interactionListener::onMediaClicked,
                        onClickEndText = {
                            interactionListener.onWhatShouldIWatchClicked(
                                sectionType = ShowMoreSectionType.MATCHES_YOUR_VIBES
                            )
                        }
                    )
                }
                if (state.featuredCollections.isNotEmpty()) {
                    CollectionListSection(
                        modifier = Modifier.padding(vertical = 16.dp),
                        collectionItems = state.featuredCollections,
                        onCollectionItemClick = interactionListener::onFeaturedCollectionClicked,
                    )
                }
                if (state.topRated.isNotEmpty()) {
                    ListSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        title = stringResource(R.string.top_rated_tv_shows),
                        endText = stringResource(R.string.show_more),
                        uiModels = state.topRated,
                        onClickItem = interactionListener::onMediaClicked,
                        onClickEndText = {
                            interactionListener.onSeeAllTopRatedClicked(
                                sectionType = ShowMoreSectionType.TOP_RATED_TV_SHOWS
                            )
                        },
                    )
                }
                if (state.recentlyViewed.isNotEmpty()) {
                    ListSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        endText = stringResource(R.string.show_more),
                        title = stringResource(R.string.you_recent_viewed),
                        uiModels = state.recentlyViewed,
                        onClickItem = interactionListener::onMediaClicked,
                        onClickEndText = {
                            interactionListener.onSeeAllRecentlyViewedClicked(
                                sectionType = ShowMoreSectionType.RECENTLY_VIEWED
                            )
                        }
                    )
                }
                if (state.yourCollections.isNotEmpty()) {
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
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .onGloballyPositioned { coordinates ->
                        topAppBarHeight = with(density) {
                            coordinates.size.height.toDp()
                        }
                    }
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {},
            ) {
                TopAppBar(
                    userName = state.userName,
                    isLoggedIn = state.isLoggedIn
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun HomeContentPreview() {
    val interactionObject = object : HomeInteractionListener {
        override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {}
        override fun onSeeAllRecentlyReleasedClicked(sectionType: ShowMoreSectionType) {}

        override fun onSeeAllTopRatedClicked(sectionType: ShowMoreSectionType) {}

        override fun onSeeAllUpcomingClicked(sectionType: ShowMoreSectionType) {}

        override fun onSeeAllRecentlyViewedClicked(sectionType: ShowMoreSectionType) {}

        override fun onWhatShouldIWatchClicked(sectionType: ShowMoreSectionType) {}

        override fun onFeaturedCollectionClicked(
            collectionId: Int,
            collectionTitle: String
        ) {
        }

        override fun onYourCollectionClicked() {}
        override fun onExploreSectionClicked() {}
        override fun onMatchSectionClicked() {}
        override fun onCollectionClick(collectionId: Int, collectionName: String) {}
    }
    CineVerseTheme(isDarkTheme = false) {
        HomeContent(
            state = HomeScreenState(
                popularity = listOf(
                    PopularMediaUi(
                        id = 1,
                        posterUrl = "data:image/jpeg;base64,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",
                        rating = 4.5f,
                        title = "The SpongeBob Movie: Sponge on the Run",
                        genres = listOf("comedy", "action", "animation"),
                        mediaType = MediaType.MOVIE
                    ),
                    PopularMediaUi(
                        id = 1,
                        posterUrl = "data:image/jpeg;base64,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",
                        rating = 4.5f,
                        title = "The SpongeBob Movie: Sponge on the Run",
                        genres = listOf("comedy", "action", "animation"),
                        mediaType = MediaType.MOVIE
                    ),
                    PopularMediaUi(
                        id = 1,
                        posterUrl = "data:image/jpeg;base64,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",
                        rating = 4.5f,
                        title = "The SpongeBob Movie: Sponge on the Run",
                        genres = listOf("comedy", "action", "animation"),
                        mediaType = MediaType.SERIES
                    ),
                    PopularMediaUi(
                        id = 1,
                        posterUrl = "data:image/jpeg;base64,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",
                        rating = 4.5f,
                        title = "The SpongeBob Movie: Sponge on the Run",
                        genres = listOf("comedy", "action", "animation"),
                        mediaType = MediaType.MOVIE
                    ),
                    PopularMediaUi(
                        id = 1,
                        posterUrl = "data:image/jpeg;base64,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",
                        rating = 4.5f,
                        title = "The SpongeBob Movie: Sponge on the Run",
                        genres = listOf("comedy", "action", "animation"),
                        mediaType = MediaType.MOVIE
                    ),
                )
            ),
            interactionListener = interactionObject,
        )
    }
}