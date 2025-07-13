package com.giraffe.cineverseapp.di

import com.giraffe.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.explore.usecase.DeleteSearchKeywordUseCase
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.series.usecase.*
import com.giraffe.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.person.usecase.GetRecentPeopleUseCase
import com.giraffe.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.person.usecase.StoreRecentPersonUseCase
import org.koin.dsl.module
import com.giraffe.movies.usecase.*
import com.giraffe.person.usecase.GetPeopleByMovieIdUseCase
import org.koin.core.module.dsl.singleOf

val useCaseModule = module {

    // Explore Use Cases
    singleOf(::GetSearchKeywordsUseCase)
    singleOf(::InsertSearchKeywordUseCase)
    singleOf(::DeleteSearchKeywordUseCase)
    singleOf(::ClearSearchHistoryUseCase)

    singleOf(::ExploreUseCases)

    singleOf(::ClearRecentSeriesUseCase)
    singleOf(::GetRecentSeriesUseCase)
    singleOf(::GetSeriesGenresUseCase)
    singleOf(::SearchSeriesByNameUseCase)
    singleOf(::StoreRecentSeriesUseCase)


    // Movies Use Cases
    singleOf(::SearchMovieByNameUseCase)
    singleOf(::GetMoviesGenresUseCase)
    singleOf(::GetMoviesByGenreUseCase)
    singleOf(::InsertMoviesUseCase)
    singleOf(::InsertGenresUseCase)
    singleOf(::ClearCacheUseCase)
    singleOf(::SetMovieRecentUseCase)
    singleOf(::GetRecentlyMoviesUseCase)
    singleOf(::ClearRecentlyMoviesUseCase)
    singleOf(::GetMovieGenresUseCase)

    singleOf(::MoviesUseCases)

    singleOf(::ClearRecentPeopleUseCase)
    singleOf(::GetRecentPeopleUseCase)
    singleOf(::SearchPeopleByNameUseCase)
    singleOf(::StoreRecentPersonUseCase)
    singleOf(::GetPeopleByMovieIdUseCase)

}