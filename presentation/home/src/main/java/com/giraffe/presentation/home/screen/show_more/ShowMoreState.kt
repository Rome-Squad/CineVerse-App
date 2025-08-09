<<<<<<<< HEAD:presentation/home/src/main/java/com/giraffe/home/screen/show_more/ShowMoreState.kt
package com.giraffe.home.screen.show_more
========
package com.giraffe.presentation.home.screen.movies_list
>>>>>>>> origin/develop:presentation/home/src/main/java/com/giraffe/presentation/home/screen/movies_list/MoviesListUiState.kt

import com.giraffe.presentation.home.screen.home.MediaType

data class ShowMoreState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<PosterUiState> = emptyList(),
    val sectionType: ShowMoreSectionType?=null,
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