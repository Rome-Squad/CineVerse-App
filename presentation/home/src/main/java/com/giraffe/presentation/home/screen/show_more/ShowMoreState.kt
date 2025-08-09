package com.giraffe.presentation.home.screen.show_more


import com.giraffe.presentation.home.model.PosterUi
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType

data class ShowMoreState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<PosterUi> = emptyList(),
    val sectionType: ShowMoreSectionType?=null,
    val isListSelected: Boolean = false,
    val errorMsgRes:Int? = null
)


