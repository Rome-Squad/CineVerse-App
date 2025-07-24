package com.giraffe.details.screens.seriesdetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.models.CastUi
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.models.SeasonUi
import com.giraffe.details.models.SeriesUi

data class SeriesDetailsScreenState(
    val seriesDetails: SeriesUi = SeriesUi(),
    val genres: List<String> = emptyList(),
    val seasons: List<SeasonUi> = emptyList(),
    val cast: List<CastUi> = emptyList(),
    val crew: Map<String, List<String>> = emptyMap(),
    val recommendedSeries: List<Poster> = emptyList(),
    val seriesReviews: List<ReviewUI> = emptyList(),

    val isLoading: Boolean = true,

    val isVisibleAddToCollectionBottomSheet: Boolean = false,
    val isVisibleGiveStarsBottomSheet: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false
)