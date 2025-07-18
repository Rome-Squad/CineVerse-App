package com.giraffe.cineverseapp.di

import com.giraffe.details.screens.castDetails.CastDetailsViewModel
import com.giraffe.details.screens.seasons.SeasonsViewModel
import com.giraffe.details.screens.seriesRecommendation.SeriesRecommendationViewModel
import com.giraffe.details.screens.seriesReview.SeriesReviewViewModel
import com.giraffe.details.screens.seriesdetails.SeriesDetailsViewModel
import com.giraffe.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ExploreViewModel)
    viewModelOf(::CastDetailsViewModel)
    viewModelOf(::SeriesDetailsViewModel)
    viewModelOf(::SeasonsViewModel)
    viewModel { (seriesId: Int) -> SeriesReviewViewModel(get(), seriesId) }
    viewModel { (seriesId: Long) -> SeriesRecommendationViewModel(get(), seriesId) }
}