package com.giraffe.home.screen.home


data class HomeScreenUiState(

    val userName: String = "",

    val matchVibes: List<HomeUiModel> = emptyList(),

    val popularity: List<PopularMediaUiModel> = emptyList(),

    val recentlyReleased: List<HomeUiModel> = emptyList(),

    val topRated: List<HomeUiModel> = emptyList(),

    val upcomingMovies: List<HomeUiModel> = emptyList(),

    val recentlyViewed: List<HomeUiModel> = emptyList(),

    val collections: List<YourCollectionUiModel> = emptyList(),

    val featuredCollections: List<FeaturedCollectionUiModel> = emptyList(),

    val isLoading: Boolean = false,

    val isError: Boolean = false
)