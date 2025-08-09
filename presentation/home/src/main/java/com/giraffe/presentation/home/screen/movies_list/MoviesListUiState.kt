package com.giraffe.presentation.home.screen.movies_list

import com.giraffe.presentation.home.screen.home.MediaType

data class MoviesListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<PosterUiState> = emptyList(),
    val moviesListTitle: String = "",
    val isListSelected: Boolean = false,
    val errorMsgRes:Int? = null
)


data class PosterUiState(
    val id: Int,
    val name: String,
    val imageUri: String,
    val rating: Float,
    val genres: String? = null,
    val time: String? = null,
    val date: String? = null,
    val mediaType: MediaType
)