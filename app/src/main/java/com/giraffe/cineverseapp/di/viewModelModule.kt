package com.giraffe.cineverseapp.di

import androidx.lifecycle.SavedStateHandle
import com.giraffe.authentication.screen.LoginViewModel
import com.giraffe.details.screens.castDetails.CastDetailsViewModel
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.details.screens.recommended.movies.RecommendedMoviesViewModel
import com.giraffe.details.screens.recommended.series.RecommendedSeriesViewModel
import com.giraffe.details.screens.seriesdetails.SeriesDetailsViewModel
import com.giraffe.explore.screen.discover.DiscoverViewModel
import com.giraffe.explore.screen.search.SearchViewModel
import com.giraffe.explore.screen.searchresult.SearchResultViewModel
import com.giraffe.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::DiscoverViewModel)
    viewModelOf(::SearchViewModel)
    viewModel { (personId: Int) -> CastDetailsViewModel(personId, get()) }
    viewModelOf(::SeriesDetailsViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::SearchResultViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RecommendedSeriesViewModel)
    viewModel { (savedStateHandle: SavedStateHandle) ->
        RecommendedMoviesViewModel(
            savedStateHandle = savedStateHandle,
            getRecommendedMovies = get(),
            getMovieGenres = get()
        )
    }
    viewModelOf(::RecommendedSeriesViewModel)
    viewModelOf(::HomeViewModel)
    //viewModelOf(::RecommendedSeriesViewModel)
    //viewModel { (seriesId: Long) -> SeriesRecommendationViewModel(get(), seriesId) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::RecommendedSeriesViewModel)
    viewModel { (seriesId: Long) -> SeriesRecommendationViewModel(get(), seriesId) }
    viewModelOf(::LoginViewModel)
}