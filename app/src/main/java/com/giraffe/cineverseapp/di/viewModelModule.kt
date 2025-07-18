package com.giraffe.cineverseapp.di

import com.giraffe.details.screens.castDetails.CastDetailsViewModel
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.details.screens.recommended.RecommendedViewModel
import com.giraffe.details.screens.seasons.SeasonsViewModel
import com.giraffe.details.screens.seriesRecommendation.SeriesRecommendationViewModel
import com.giraffe.details.screens.seriesdetails.SeriesDetailsViewModel
import com.giraffe.explore.screen.discover.DiscoverViewModel
import com.giraffe.explore.screen.search.SearchViewModel
import com.giraffe.explore.screen.searchresult.SearchResultViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::DiscoverViewModel)
    viewModelOf(::SearchViewModel)
    viewModel { (personId: Int) -> CastDetailsViewModel(personId, get()) }
    viewModelOf(::SeriesDetailsViewModel)
    viewModelOf(::SeasonsViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::SearchResultViewModel)
    viewModelOf(::RecommendedViewModel)
    viewModel { (seriesId: Long) -> SeriesRecommendationViewModel(get(), seriesId) }
}