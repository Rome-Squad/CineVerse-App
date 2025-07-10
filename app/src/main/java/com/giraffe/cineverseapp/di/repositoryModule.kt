package com.giraffe.cineverseapp.di

import com.giraffe.explore.ExploreRepositoryImpl
import com.giraffe.explore.repository.ExploreRepository
import com.giraffe.person.PersonRepositoryImpl
import com.giraffe.person.repository.PersonRepository
import com.giraffe.person.util.ConnectionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { ConnectionChecker(androidContext()) }
    single<ExploreRepository> { ExploreRepositoryImpl(get(), get()) }
    single<PersonRepository> { PersonRepositoryImpl(get(), get()) }
}