package com.giraffe.cineverseapp.di

import com.giraffe.media.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.media.explore.usecase.DeleteSearchKeywordUseCase
import com.giraffe.media.explore.usecase.ExploreUseCases
import com.giraffe.media.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.media.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.media.movies.usecase.AddMovieRatingUseCase
import com.giraffe.media.movies.usecase.ClearCacheUseCase
import com.giraffe.media.movies.usecase.ClearRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetMovieDetailsUseCase
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetMovieReviewsUseCase
import com.giraffe.media.movies.usecase.GetMoviesByGenreUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetUserMovieRatingUseCase
import com.giraffe.media.movies.usecase.InsertGenresUseCase
import com.giraffe.media.movies.usecase.InsertMoviesUseCase
import com.giraffe.media.movies.usecase.MoviesUseCases
import com.giraffe.media.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.media.movies.usecase.SetMovieRecentUseCase
import com.giraffe.media.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.media.person.usecase.GetPeopleByMovieIdUseCase
import com.giraffe.media.person.usecase.GetPeopleBySeriesIdUseCase
import com.giraffe.media.person.usecase.GetPersonDetailsUseCase
import com.giraffe.media.person.usecase.GetRecentPeopleUseCase
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.person.usecase.StoreRecentPersonUseCase
import com.giraffe.media.series.usecase.ClearRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.SearchSeriesByNameUseCase
import com.giraffe.media.series.usecase.StoreRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
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

    // NEW: Add UseCases for Movie Details and Rating
    single { GetMovieDetailsUseCase(get()) }
    single { GetMovieReviewsUseCase(get()) }
    single { AddMovieRatingUseCase(get()) }
    single { GetUserMovieRatingUseCase(get()) }

    single {
        MoviesUseCases(
            searchMovieByNameUseCase = SearchMovieByNameUseCase(get()),
            getMoviesGenresUseCase = GetMoviesGenresUseCase(get()),
            getMoviesByGenreUseCase = GetMoviesByGenreUseCase(get()),
            insertMoviesUseCase = InsertMoviesUseCase(get()),
            insertGenresUseCase = InsertGenresUseCase(get()),
            clearCacheUseCase = ClearCacheUseCase(get()),
            setMovieRecentUseCase = SetMovieRecentUseCase(get()),
            getRecentlyMovies = GetRecentlyMoviesUseCase(get()),
            clearRecentlyMovies = ClearRecentlyMoviesUseCase(get()),
            getMovieGenresUseCase = GetMovieGenresUseCase(get()),
            getMovieDetailsUseCase = GetMovieDetailsUseCase(get()),
            getMovieReviewsUseCase = GetMovieReviewsUseCase(get()),
            addMovieRatingUseCase = AddMovieRatingUseCase(get()),
            getUserMovieRatingUseCase = GetUserMovieRatingUseCase(get())
        )
    }

    // Person UseCases
    singleOf(::ClearRecentPeopleUseCase)
    singleOf(::GetRecentPeopleUseCase)
    singleOf(::SearchPeopleByNameUseCase)
    singleOf(::StoreRecentPersonUseCase)
    singleOf(::GetPeopleByMovieIdUseCase)
    singleOf(::GetPeopleBySeriesIdUseCase)
    singleOf(::GetPersonDetailsUseCase)
}