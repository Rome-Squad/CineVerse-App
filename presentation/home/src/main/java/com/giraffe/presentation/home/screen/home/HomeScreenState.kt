package com.giraffe.presentation.home.screen.home

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
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val isLoggedIn: Boolean = false,
)