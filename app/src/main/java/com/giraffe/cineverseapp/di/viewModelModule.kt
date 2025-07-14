package com.giraffe.cineverseapp.di

import com.giraffe.details.screens.castDetails.CastDetailsViewModel
import com.giraffe.explore.screen.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::CastDetailsViewModel)
}