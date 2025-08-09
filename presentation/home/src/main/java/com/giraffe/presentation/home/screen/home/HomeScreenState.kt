package com.giraffe.presentation.home.screen.home

import com.giraffe.presentation.home.model.FeaturedCollectionUiModel
import com.giraffe.presentation.home.model.HomeUiModel
import com.giraffe.presentation.home.model.PopularMediaUiModel
import com.giraffe.presentation.home.model.YourCollectionUiModel


data class HomeScreenState(
    val userName: String = "",
    val matchVibes: List<HomeUiModel> = emptyList(),
    val popularity: List<PopularMediaUiModel> = emptyList(),
    val recentlyReleased: List<HomeUiModel> = emptyList(),
    val topRated: List<HomeUiModel> = emptyList(),
    val upcomingMovies: List<HomeUiModel> = emptyList(),
    val recentlyViewed: List<HomeUiModel> = emptyList(),
    val yourCollections: List<YourCollectionUiModel> = emptyList(),
    val featuredCollections: List<FeaturedCollectionUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsgRes: Int? = null,
    val isGenericError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isLoggedIn: Boolean = false,
)