package com.giraffe.cineverseapp.di

import com.giraffe.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.explore.usecase.DeleteSearchKeywordUseCase
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.series.usecase.*
import org.koin.dsl.module

val useCaseModule = module {

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
    single { ClearRecentSeriesUseCase(get()) }
    single { GetRecentSeriesUseCase(get()) }
    single { GetSeriesGenresUseCase(get()) }
    single { SearchSeriesByNameUseCase(get()) }
    single { StoreRecentSeriesUseCase(get()) }


}