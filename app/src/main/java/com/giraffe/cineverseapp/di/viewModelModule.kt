package com.giraffe.cineverseapp.di

import com.giraffe.explore.ExploreViewModel
import com.giraffe.details.screens.castDetails.CastDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ExploreViewModel)
    viewModelOf(::CastDetailsViewModel)
}