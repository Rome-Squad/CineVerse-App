package com.giraffe.cineverseapp.di

import com.giraffe.explore.ExploreRepositoryImpl
import com.giraffe.explore.repository.ExploreRepository
import com.giraffe.movie.MoviesRepositoryImpl
import com.giraffe.movies.repository.MoviesRepository
import com.giraffe.person.PersonRepositoryImpl
import com.giraffe.person.repository.PersonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ExploreRepository> {
        ExploreRepositoryImpl(
            cache = get(),
            remote = get()
        )
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            cache = get(),
            remote = get(),
            searchHistory = get()
        )
    }

    single<PersonRepository> { PersonRepositoryImpl(get(), get()) }
}