package com.giraffe.cineverseapp.di

import com.giraffe.media.explore.ExploreRepositoryImpl
import com.giraffe.media.explore.repository.ExploreRepository
import com.giraffe.media.series.SeriesRepositoryImpl
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.movie.MoviesRepositoryImpl
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.person.PersonRepositoryImpl
import com.giraffe.media.person.repository.PersonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ExploreRepository> { ExploreRepositoryImpl(get(), get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get(), get()) }

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
            sessionManager = get()
        )
    }

    single<PersonRepository> { PersonRepositoryImpl(get(), get()) }
}