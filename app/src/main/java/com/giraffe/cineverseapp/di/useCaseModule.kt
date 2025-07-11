package com.giraffe.cineverseapp.di

import com.giraffe.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.explore.usecase.DeleteSearchKeywordUseCase
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.person.usecase.GetRecentPeopleUseCase
import com.giraffe.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.person.usecase.StoreRecentPersonUseCase
import org.koin.dsl.module
import com.giraffe.movies.usecase.*

val useCaseModule = module {

    // Explore Use Cases
    single { GetSearchKeywordsUseCase(get()) }
    single { InsertSearchKeywordUseCase(get()) }
    single { DeleteSearchKeywordUseCase(get()) }
    single { ClearSearchHistoryUseCase(get()) }

    single {
        ExploreUseCases(
            getSearchKeywords = GetSearchKeywordsUseCase(get()),
            insertSearchKeyword = InsertSearchKeywordUseCase(get()),
            deleteSearchKeyword = DeleteSearchKeywordUseCase(get()),
            clearSearchHistory = ClearSearchHistoryUseCase(get())
        )
    }

    // Movies Use Cases
    single { SearchMovieByNameUseCase(get()) }
    single { GetMoviesGenresUseCase(get()) }
    single { GetMoviesByGenreUseCase(get()) }
    single { InsertMoviesUseCase(get()) }
    single { InsertGenresUseCase(get()) }
    single { ClearCacheUseCase(get()) }
    single { SetMovieRecentUseCase(get()) }
    single { GetRecentlyMoviesUseCase(get()) }
    single { ClearRecentlyMoviesUseCase(get()) }
    single { GetMovieGenresUseCase(get()) }

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
        )
    }

    single { ClearRecentPeopleUseCase(get()) }
    single { GetRecentPeopleUseCase(get()) }
    single { SearchPeopleByNameUseCase(get()) }
    single { StoreRecentPersonUseCase(get()) }
}