package com.giraffe.home.screen.home


data class HomeScreenUiState(

    val userName: String = "",

    val matchVibes: List<HomeUiModel> = emptyList(),

    val popularity: List<PopularMediaUiModel> = emptyList(),

    val recentlyReleased: List<HomeUiModel> = emptyList(),

    val topRated: List<HomeUiModel> = emptyList(),

    val upcomingMovies: List<HomeUiModel> = listOf(
        HomeUiModel(
            id = 1111,
            title = "Title",
            posterUrl = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
            rating = 0f,
            mediaType = MediaType.MOVIE
        ),
        HomeUiModel(
            id = 2,
            title = "Title",
            posterUrl = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
            rating = 0f,
            mediaType = MediaType.MOVIE
        ),
        HomeUiModel(
            id = 3,
            title = "Title",
            posterUrl = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
            rating = 0f,
            mediaType = MediaType.MOVIE
        ),
        HomeUiModel(
            id = 4,
            title = "Title",
            posterUrl = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
            rating = 0f,
            mediaType = MediaType.MOVIE
        ),
        HomeUiModel(
            id = 5,
            title = "Title",
            posterUrl = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
            rating = 0f,
            mediaType = MediaType.MOVIE
        ),

    ),

    val recentlyViewed: List<HomeUiModel> = emptyList(),

    val collections: List<YourCollectionUiModel> = emptyList(),

    val featuredCollections: List<FeaturedCollectionUiModel> = emptyList(),

    val isLoading: Boolean = false,

    val isError: Boolean = false
)