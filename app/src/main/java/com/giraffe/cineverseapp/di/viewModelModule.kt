package com.giraffe.cineverseapp.di

import com.giraffe.details.screen.MovieDetails
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.explore.screen.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::MovieDetailsViewModel)
}