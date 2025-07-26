package com.giraffe.cineverseapp.di

import com.giraffe.media.explore.ExploreRepositoryImpl
import com.giraffe.media.explore.repository.ExploreRepository
import com.giraffe.media.movie.MoviesRepositoryImpl
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.person.PersonRepositoryImpl
import com.giraffe.media.person.repository.PersonRepository
import com.giraffe.media.series.SeriesRepositoryImpl
import com.giraffe.media.series.repository.SeriesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ExploreRepositoryImpl) bind ExploreRepository::class
    singleOf(::SeriesRepositoryImpl) bind SeriesRepository::class
    singleOf(::MoviesRepositoryImpl) bind MoviesRepository::class
    singleOf(::PersonRepositoryImpl) bind PersonRepository::class
}