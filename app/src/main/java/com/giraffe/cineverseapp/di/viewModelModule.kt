package com.giraffe.cineverseapp.di

import com.giraffe.details.screens.castDetails.CastDetailsViewModel
import com.giraffe.details.screens.moviedetails.MovieDetailsViewModel
import com.giraffe.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ExploreViewModel)
    viewModelOf(::CastDetailsViewModel)
    viewModelOf(::MovieDetailsViewModel)
}