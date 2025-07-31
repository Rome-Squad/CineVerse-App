package com.giraffe.home.screen.movies_list

import com.giraffe.home.screen.home.MediaType

data class MoviesListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<PosterUiState> = emptyList(),
    val moviesListTitle: String = "",
    val isListSelected: Boolean = false
)


data class PosterUiState(
    val id: Int,
    val name: String,
    val imageUri: String,
    val rating: Float,
    val genres: List<String>,
    val time: String? = null,
    val date: String,
    val mediaType: MediaType
)