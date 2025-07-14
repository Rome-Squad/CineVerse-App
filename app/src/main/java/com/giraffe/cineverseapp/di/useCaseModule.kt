package com.giraffe.cineverseapp.di

import com.giraffe.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.explore.usecase.DeleteSearchKeywordUseCase
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.movies.usecase.ClearCacheUseCase
import com.giraffe.movies.usecase.ClearRecentlyMoviesUseCase
import com.giraffe.movies.usecase.GetMovieGenresUseCase
import com.giraffe.movies.usecase.GetMoviesByGenreUseCase
import com.giraffe.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.movies.usecase.InsertGenresUseCase
import com.giraffe.movies.usecase.InsertMoviesUseCase
import com.giraffe.movies.usecase.MoviesUseCases
import com.giraffe.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.movies.usecase.SetMovieRecentUseCase
import com.giraffe.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.person.usecase.GetPeopleByMovieIdUseCase
import com.giraffe.person.usecase.GetPeopleBySeriesIdUseCase
import com.giraffe.person.usecase.GetRecentPeopleUseCase
import com.giraffe.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.person.usecase.StoreRecentPersonUseCase
import com.giraffe.series.usecase.ClearRecentSeriesUseCase
import com.giraffe.series.usecase.GetRecentSeriesUseCase
import com.giraffe.series.usecase.GetSeriesGenresUseCase
import com.giraffe.series.usecase.SearchSeriesByNameUseCase
import com.giraffe.series.usecase.StoreRecentSeriesUseCase
import com.giraffe.series.usecase.GetRecommendedSeriesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {

    // Explore UseCases
    singleOf(::GetSearchKeywordsUseCase)
    singleOf(::InsertSearchKeywordUseCase)
    singleOf(::DeleteSearchKeywordUseCase)
    singleOf(::ClearSearchHistoryUseCase)

    single {
        ExploreUseCases(
            getSearchKeywords = get(),
            insertSearchKeyword = get(),
            deleteSearchKeyword = get(),
            clearSearchHistory = get()
        )
    }

    // Series UseCases
    singleOf(::ClearRecentSeriesUseCase)
    singleOf(::GetRecentSeriesUseCase)
    singleOf(::GetSeriesGenresUseCase)
    singleOf(::SearchSeriesByNameUseCase)
    singleOf(::StoreRecentSeriesUseCase)
    singleOf(::GetRecommendedSeriesUseCase)

    // Movie UseCases
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

    single {
        MoviesUseCases(
            searchMovieByNameUseCase = get(),
            getMoviesGenresUseCase = get(),
            getMoviesByGenreUseCase = get(),
            insertMoviesUseCase = get(),
            insertGenresUseCase = get(),
            clearCacheUseCase = get(),
            setMovieRecentUseCase = get(),
            getRecentlyMovies = get(),
            clearRecentlyMovies = get(),
            getMovieGenresUseCase = get(),
        )
    }

    // Person UseCases
    singleOf(::ClearRecentPeopleUseCase)
    singleOf(::GetRecentPeopleUseCase)
    singleOf(::SearchPeopleByNameUseCase)
    singleOf(::StoreRecentPersonUseCase)
    singleOf(::GetPeopleByMovieIdUseCase)
    singleOf(::GetPeopleBySeriesIdUseCase)
}