package com.giraffe.cineverseapp.di

import com.giraffe.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.explore.usecase.DeleteSearchKeywordUseCase
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.movies.usecase.SearchMovieByNameUseCase
import org.koin.dsl.module
val useCaseModule = module {

    single { SearchMovieByNameUseCase(get()) }
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

}