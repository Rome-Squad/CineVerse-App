package com.giraffe.presentation.home.screen.show_more


import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType

data class ShowMoreScreenState(
    val isLoading: Boolean = false,
    val mediaList: List<ShowMorePoster> = emptyList(),
    val sectionType: ShowMoreSectionType? = null,
    val isListSelected: Boolean = false,
    val isNoInternet: Boolean = false
)


