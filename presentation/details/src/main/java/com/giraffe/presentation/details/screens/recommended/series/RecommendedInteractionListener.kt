package com.giraffe.presentation.details.screens.recommended.series

interface RecommendedInteractionListener {
    fun onSeriesClick(seriesId: Int)

    fun onBackClick()

    fun onRetryClick()
}