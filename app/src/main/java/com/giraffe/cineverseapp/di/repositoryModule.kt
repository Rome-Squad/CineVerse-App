package com.giraffe.cineverseapp.di

import com.giraffe.explore.ExploreRepositoryImpl
import com.giraffe.explore.repository.ExploreRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ExploreRepository> { ExploreRepositoryImpl(get(), get()) }
}