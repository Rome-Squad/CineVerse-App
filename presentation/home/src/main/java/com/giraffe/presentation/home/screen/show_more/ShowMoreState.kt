package com.giraffe.presentation.home.screen.show_more


import com.giraffe.presentation.home.model.PosterUiModel
import com.giraffe.presentation.home.navigation.show_more.ShowMoreSectionType

data class ShowMoreState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<PosterUiModel> = emptyList(),
    val sectionType: ShowMoreSectionType?=null,
    val isListSelected: Boolean = false,
    val errorMsgRes:Int? = null
)


