package com.giraffe.cineverseapp.di

import com.giraffe.explore.ExploreRepositoryImpl
import com.giraffe.explore.repository.ExploreRepository
import com.giraffe.person.PersonRepositoryImpl
import com.giraffe.person.repository.PersonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ExploreRepository> { ExploreRepositoryImpl(get(), get()) }
    single<PersonRepository> { PersonRepositoryImpl(get(), get()) }
}