package com.giraffe.profile.screens.history


data class HistoryScreenUiStateUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<PosterUiState> = emptyList(),
    val historyListTitle: String = "",
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
    val mediaType: com.giraffe.profile.screens.history.MediaType
)