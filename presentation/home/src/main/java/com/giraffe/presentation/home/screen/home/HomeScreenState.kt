package com.giraffe.presentation.home.screen.home

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.home.model.FeaturedCollectionType
import com.giraffe.presentation.home.model.FeaturedCollectionUi
import com.giraffe.presentation.home.model.PopularMediaUi
import com.giraffe.presentation.home.model.Poster
import com.giraffe.presentation.home.model.YourCollectionUi


data class HomeScreenState(
    val userName: String = "",
    val matchVibes: List<Poster> = emptyList(),
    val popularity: List<PopularMediaUi> = emptyList(),
    val recentlyReleased: List<Poster> = emptyList(),
    val topRated: List<Poster> = emptyList(),
    val upcomingMovies: List<Poster> = emptyList(),
    val recentlyViewed: List<Poster> = emptyList(),
    val yourCollections: List<YourCollectionUi> = emptyList(),
    val featuredCollections: List<FeaturedCollectionUi> = FeaturedCollectionType.entries.map {
        FeaturedCollectionUi(
            it
        )
    },
    val isLoadingUserName: Boolean = true,
    val isLoadingPopularity: Boolean = true,
    val isLoadingRecentlyReleased: Boolean = true,
    val isLoadingUpcomingMovies: Boolean = true,
    val isLoadingTopRatedSeries: Boolean = true,
    val isNoInternet: Boolean = false,
    val isLoggedIn: Boolean = false,
    val moviesGenres: List<Genre> = emptyList(),
    val seriesGenres: List<Genre> = emptyList()
)