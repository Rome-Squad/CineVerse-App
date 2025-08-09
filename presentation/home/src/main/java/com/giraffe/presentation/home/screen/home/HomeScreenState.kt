package com.giraffe.presentation.home.screen.home

import com.giraffe.presentation.home.model.FeaturedCollectionUi
import com.giraffe.presentation.home.model.HomeUi
import com.giraffe.presentation.home.model.PopularMediaUi
import com.giraffe.presentation.home.model.YourCollectionUi


data class HomeScreenState(
    val userName: String = "",
    val matchVibes: List<HomeUi> = emptyList(),
    val popularity: List<PopularMediaUi> = emptyList(),
    val recentlyReleased: List<HomeUi> = emptyList(),
    val topRated: List<HomeUi> = emptyList(),
    val upcomingMovies: List<HomeUi> = emptyList(),
    val recentlyViewed: List<HomeUi> = emptyList(),
    val yourCollections: List<YourCollectionUi> = emptyList(),
    val featuredCollections: List<FeaturedCollectionUi> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsgRes: String? = null,
    val isGenericError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isLoggedIn: Boolean = false,
)