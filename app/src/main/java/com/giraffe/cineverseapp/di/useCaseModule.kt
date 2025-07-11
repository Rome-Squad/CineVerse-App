package com.giraffe.cineverseapp.di

import com.giraffe.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.explore.usecase.DeleteSearchKeywordUseCase
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.person.usecase.GetRecentPeopleUseCase
import com.giraffe.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.person.usecase.StoreRecentPersonUseCase
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

    single { ClearRecentPeopleUseCase(get()) }
    single { GetRecentPeopleUseCase(get()) }
    single { SearchPeopleByNameUseCase(get()) }
    single { StoreRecentPersonUseCase(get()) }
}