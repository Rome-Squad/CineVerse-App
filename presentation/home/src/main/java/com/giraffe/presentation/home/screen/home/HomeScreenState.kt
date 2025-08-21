package com.giraffe.presentation.home.screen.home

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.home.model.FeaturedCollectionType
import com.giraffe.presentation.home.model.FeaturedCollectionUi
import com.giraffe.presentation.home.model.PopularMediaUi
import com.giraffe.presentation.home.model.Poster
import com.giraffe.presentation.home.model.YourCollectionUi
import com.giraffe.user.entity.ContentPreference

data class HomeScreenState(
    val userName: String = "",
    val moviesGenres: List<Genre> = emptyList(),
    val seriesGenres: List<Genre> = emptyList(),
    val matchVibes: List<Poster> = emptyList(),
    val popularity: List<PopularMediaUi> = emptyList(),
    val recentlyReleased: List<Poster> = emptyList(),
    val topRated: List<Poster> = emptyList(),
    val upcomingMovies: List<Poster> = emptyList(),
    val recentlyViewed: List<Poster> = emptyList(),
    val yourCollections: List<YourCollectionUi> = emptyList(),
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
    val featuredCollections: List<FeaturedCollectionUi> = FeaturedCollectionType.entries.map {
        FeaturedCollectionUi(
            it
        )
    },

    val isLoadingPopularity: Boolean = true,
    val isLoadingRecentlyReleased: Boolean = true,
    val isLoadingMatchesYourVibe: Boolean = true,
    val isLoadingRecentlyViewed: Boolean = true,
    val isLoadingUpcoming: Boolean = true,
    val isLoadingTopRated: Boolean = true,

    val hasPopularityError: Boolean = false,
    val hasRecentlyReleasedError: Boolean = false,
    val hasMatchesYourVibeError: Boolean = false,
    val hasRecentlyViewedError: Boolean = false,
    val hasUpcomingError: Boolean = false,
    val hasTopRatedError: Boolean = false,
)