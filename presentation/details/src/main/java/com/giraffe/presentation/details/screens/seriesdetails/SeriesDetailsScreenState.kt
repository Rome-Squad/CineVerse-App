package com.giraffe.presentation.details.screens.seriesdetails

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.model.CastUi
import com.giraffe.presentation.details.model.ReviewUI
import com.giraffe.presentation.details.model.SeasonUi
import com.giraffe.presentation.details.model.SeriesUi

data class SeriesDetailsScreenState(
    val seriesUi: SeriesUi = SeriesUi(),
    val genres: List<String> = emptyList(),
    val seasons: List<SeasonUi> = emptyList(),
    val cast: List<CastUi> = emptyList(),
    val crew: Map<String, List<String>> = emptyMap(),
    val recommendedSeries: List<Poster> = emptyList(),
    val seriesReviews: List<ReviewUI> = emptyList(),
    val currentRating: Int = 0,

    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,

    val isVisibleAddToCollectionBottomSheet: Boolean = false,
    val isVisibleGiveStarsBottomSheet: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false,

    val animationProgress: Animatable<Float, AnimationVector1D> = Animatable(0f)
)