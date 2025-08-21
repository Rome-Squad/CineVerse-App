package com.giraffe.presentation.home.screen.categoryMedia


import androidx.paging.PagingData
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.user.entity.ContentPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class CategoryMediaScreenState(
    val isLoading: Boolean = true,
    val mediaFlow: Flow<PagingData<PosterMedia>> = flowOf(),
    val sectionType: CategoryMediaSectionType? = null,
    val isListSelected: Boolean = false,
    val isNoInternet: Boolean = false,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
)


