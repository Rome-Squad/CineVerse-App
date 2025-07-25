package com.giraffe.home.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.R
import com.giraffe.home.components.AdvertisementSection
import com.giraffe.home.components.Carousel
import com.giraffe.home.components.CollectionItemData
import com.giraffe.home.components.CollectionListSection
import com.giraffe.home.components.HomeUiListSection
import com.giraffe.home.components.HorizontalDivider
import com.giraffe.home.components.TopAppBar
import com.giraffe.home.components.UserCollection
import com.giraffe.home.components.YourCollectionsSections
import com.giraffe.home.screen.movies_list.MovieSectionType
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToMoviesListScreen: (sectionType: String, sectionTitle: String) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToRecentlyReleasedList -> {
                    navigateToMoviesListScreen(
                        MovieSectionType.RECENTLY_RELEASED,
                        effect.sectionTitle
                    )
                }

                is HomeEffect.NavigateToUpcomingList -> {
                    navigateToMoviesListScreen(
                        MovieSectionType.UPCOMING_MOVIES,
                        effect.sectionTitle
                    )
                }

                is HomeEffect.NavigateToRecommendedList -> {
                    navigateToMoviesListScreen(
                        MovieSectionType.MATCHES_YOUR_VIBES,
                        effect.sectionTitle
                    )
                }

                is HomeEffect.NavigateToTopRatedList -> {
                    navigateToMoviesListScreen(
                        MovieSectionType.TOP_RATED_TV_SHOWS,
                        effect.sectionTitle
                    )
                }

                is HomeEffect.NavigateToRecentlyViewedList -> {
                    navigateToMoviesListScreen(
                        MovieSectionType.RECENTLY_VIEWED,
                        effect.sectionTitle
                    )
                }

                is HomeEffect.NavigateToMovieDetails -> navigateToMoviesDetailsScreen(effect.movieId)
                is HomeEffect.NavigateToSeriesDetails -> navigateToSeriesDetailsScreen(effect.seriesId)
                is HomeEffect.ShowError -> {}
            }
        }
    }
    HomeContent(
        state = state,
        interactionListener = viewModel
    )
}

@Composable
fun HomeContent(
    state: HomeScreenUiState,
    interactionListener: HomeInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .statusBarsPadding()
    ) {
        stickyHeader {
            TopAppBar(userName = state.userName)
        }
        item {
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 9.dp, bottom = 16.dp),
                color = Theme.color.stroke.primary
            )
        }
        item {
            Carousel(
                movieCards = state.popularity,
                onClickItem = interactionListener::onMediaClicked
            )
        }
        item {
            HomeUiListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                title = stringResource(R.string.recently_released),
                endText = stringResource(R.string.show_more),
                uiModels = state.recentlyReleased,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onSeeAllRecentlyReleasedClicked(
                        sectionTitle = "Recently Released",
                        sectionType = MovieSectionType.RECENTLY_RELEASED
                    )
                }
            )
        }
        item {
            AdvertisementSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                title = stringResource(R.string.what_should_i_watch),
                cardTitle = stringResource(R.string.let_us_choose_for_you),
                caption = stringResource(R.string.we_ll_help_you_skip_the_scroll_and_go_straight_to_the_good_stuff),
                onCardClick = {},
            )
        }
        item {
            HomeUiListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                title = stringResource(R.string.upcoming_movies),
                endText = stringResource(R.string.show_more),
                uiModels = state.upcomingMovies,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onSeeAllUpcomingClicked(
                        sectionTitle = "Upcoming Movies",
                        sectionType = MovieSectionType.UPCOMING_MOVIES
                    )
                }
            )
        }
        item {
            HomeUiListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                title = stringResource(R.string.matches_your_vibe),
                endText = stringResource(R.string.show_more),
                uiModels = state.matchVibes,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onWhatShouldIWatchClicked(
                        sectionTitle = "Matches Your Vibe",
                        sectionType = MovieSectionType.MATCHES_YOUR_VIBES
                    )
                }
            )
        }
        item {
            CollectionListSection(
                modifier = Modifier.padding(bottom = 32.dp),
                collectionItems = featuredCollection
            )
        }
        item {
            HomeUiListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                title = stringResource(R.string.top_rated_tv_shows),
                endText = stringResource(R.string.show_more),
                uiModels = state.topRated,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onSeeAllTopRatedClicked(
                        sectionTitle = "Top Rated TV Shows",
                        sectionType = MovieSectionType.TOP_RATED_TV_SHOWS
                    )
                },
            )
        }
        item {
            HomeUiListSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                endText = stringResource(R.string.show_more),
                title = stringResource(R.string.you_recent_viewed),
                uiModels = state.recentlyViewed,
                onClickItem = interactionListener::onMediaClicked,
                onClickEndText = {
                    interactionListener.onSeeAllRecentlyViewedClicked(
                        sectionTitle = "Recently Viewed",
                        sectionType = MovieSectionType.RECENTLY_VIEWED
                    )
                }
            )
        }
        item {
            YourCollectionsSections(
                modifier = Modifier.padding(bottom = 32.dp),
                collectionItems = yourCollections
            )
        }
        item {
            AdvertisementSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 50.dp),
                title = stringResource(R.string.need_more_to_watch),
                cardTitle = stringResource(R.string.browse_everything),
                caption = stringResource(R.string.explore_all_movies_and_series),
            )
        }
    }
}

val yourCollections = listOf(
    UserCollection("My Favorite TV Shows", "5 shows"),
    UserCollection("Animated Series", "4 shows"),
    UserCollection("My Watchlist", "10 movies"),
    UserCollection("Documentaries", "6 movies"),
)
val featuredCollection = listOf(
    CollectionItemData(
        image = "https://drive.google.com/uc?export=download&id=16psefCb52QbPtMbCCNbAtOocHq6dr3ol",
        collectionType = "Late-Night Thrills"
    ),
    CollectionItemData(
        image = "https://drive.google.com/uc?export=download&id=16psefCb52QbPtMbCCNbAtOocHq6dr3ol",
        collectionType = "Action"
    ),
    CollectionItemData(
        image = "https://drive.google.com/uc?export=download&id=16psefCb52QbPtMbCCNbAtOocHq6dr3ol",
        collectionType = "Crime"
    ),
    CollectionItemData(
        image = "https://drive.google.com/uc?export=download&id=16psefCb52QbPtMbCCNbAtOocHq6dr3ol",
        collectionType = "Drama"
    )
)

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun HomeContentPreview() {
    val interactionObject = object : HomeInteractionListener {
        override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
            TODO("Not yet implemented")
        }

        override fun onSeeAllRecentlyReleasedClicked(sectionTitle: String, sectionType: String) {
            TODO("Not yet implemented")
        }

        override fun onSeeAllTopRatedClicked(sectionTitle: String, sectionType: String) {
            TODO("Not yet implemented")
        }

        override fun onSeeAllUpcomingClicked(sectionTitle: String, sectionType: String) {
            TODO("Not yet implemented")
        }

        override fun onSeeAllRecentlyViewedClicked(sectionTitle: String, sectionType: String) {
            TODO("Not yet implemented")
        }

        override fun onWhatShouldIWatchClicked(sectionTitle: String, sectionType: String) {
            TODO("Not yet implemented")
        }

    }
    CineVerseTheme(isDarkTheme = false) {
        HomeContent(
            state = HomeScreenUiState(),
            interactionListener = interactionObject,
//            navigateToMoviesListScreen = { sectionType, sectionTitle -> },
//            navigateToMoviesDetailsScreen = {},
//            navigateToSeriesDetailsScreen = {},
        )
    }
}