package com.giraffe.presentation.home.screen.show_more


import androidx.paging.PagingData
import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ShowMoreScreenState(
    val isLoading: Boolean = false,
    val mediaFlow: Flow<PagingData<ShowMorePoster>> = flowOf(),
    val sectionType: ShowMoreSectionType? = null,
    val isListSelected: Boolean = false,
    val isNoInternet: Boolean = false
)


