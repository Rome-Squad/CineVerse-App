package com.giraffe.cineverseapp.di

import com.giraffe.explore.ExploreRepositoryImpl
import com.giraffe.explore.repository.ExploreRepository
import com.giraffe.series.SeriesRepositoryImpl
import com.giraffe.series.repository.SeriesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ExploreRepository> { ExploreRepositoryImpl(get(), get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get(), get()) }

}