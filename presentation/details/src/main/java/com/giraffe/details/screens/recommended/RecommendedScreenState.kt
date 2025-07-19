package com.giraffe.details.screens.recommended

import androidx.paging.PagingData
import com.giraffe.designsystem.uimodel.Poster

data class RecommendedScreenState(
    val recommended: PagingData<Poster> = PagingData.empty(),
    val title: String = ""
)
