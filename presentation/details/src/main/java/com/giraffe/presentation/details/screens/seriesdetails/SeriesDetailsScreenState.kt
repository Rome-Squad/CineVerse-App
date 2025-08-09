package com.giraffe.presentation.details.screens.seriesdetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.presentation.details.model.CastUi
import com.giraffe.presentation.details.model.ReviewUI
import com.giraffe.presentation.details.model.SeasonUi
import com.giraffe.presentation.details.model.SeriesUi

data class SeriesDetailsScreenState(
    val seriesDetails: SeriesUi = SeriesUi(),
    val genres: List<String> = emptyList(),
    val seasons: List<SeasonUi> = emptyList(),
    val cast: List<CastUi> = emptyList(),
    val crew: Map<String, List<String>> = emptyMap(),
    val recommendedSeries: List<Poster> = emptyList(),
    val seriesReviews: List<ReviewUI> = emptyList(),

    val isLoading: Boolean = true,
    val errorMessage: Int? = null,
    val isNetworkError: Boolean = false,
    val isVisibleAddToCollectionBottomSheet: Boolean = false,
    val isVisibleGiveStarsBottomSheet: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false,
    val currentRating: Int = 0
)