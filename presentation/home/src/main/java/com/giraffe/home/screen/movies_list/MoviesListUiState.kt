package com.giraffe.home.screen.movies_list

data class MoviesListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<PosterUiState> = dummyData,
    val page: Int = 1,
    val totalPages: Int = 1,
    val moviesListTitle: String = "Movies List Section",
    val isListSelected: Boolean = false
)

val dummyData = listOf(
    PosterUiState(
        id = 1,
        name = "Movie Title 1",
        imageUri = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
        rating = 4.7f,
        genres = "Drama, Action , Thriller",
        time = "2h 30m",
        date = "2023-10-01"
    ), PosterUiState(
        id = 2,
        name = "Movie Title 1",
        imageUri = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
        rating = 4.7f,
        genres = "Drama, Action , Thriller",
        time = "2h 30m",
        date = "2023-10-01"
    ), PosterUiState(
        id = 3,
        name = "Movie Title 1",
        imageUri = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
        rating = 4.7f,
        genres = "Drama, Action , Thriller",
        time = "2h 30m",
        date = "2023-10-01"
    ), PosterUiState(
        id = 4,
        name = "Movie Title 1",
        imageUri = "https://image.tmdb.org/t/p/w500/cEQMqB3ahd4mfeUN6VGC0ouVnZZ.jpg",
        rating = 4.7f,
        genres = "Drama, Action , Thriller",
        time = "2h 30m",
        date = "2023-10-01"
    )
)

data class PosterUiState(
    val id: Int,
    val name: String,
    val imageUri: String,
    val rating: Float,
    val genres: String? = null,
    val time: String? = null,
    val date: String? = null
)