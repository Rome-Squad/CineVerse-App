package com.giraffe.cineverseapp.di

import com.giraffe.movies.usecase.SearchMovieByNameUseCase
import org.koin.dsl.module
val useCaseModule = module {

    single { SearchMovieByNameUseCase(get()) }


}