package com.giraffe.cineverseapp.di

import com.giraffe.media.explore.LocalSearchDataSourceImpl
import com.giraffe.media.explore.cleaner.KeywordsCacheCleaner
import com.giraffe.media.explore.cleaner.KeywordsCacheCleanerImp
import com.giraffe.media.explore.datasource.local.LocalSearchDataSource
import com.giraffe.media.movie.MovieLocalDataSourceImp
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.person.PersonLocalDataSourceImp
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.series.SeriesRoomLocalDateSource
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.local.OnboardingLocalDataSource
import com.giraffe.repository.datasource.local.SettingsLocalDataSource
import com.giraffe.user.AuthenticationLocalDataSourceImpl
import com.giraffe.user.OnboardingLocalDataSourceImpl
import com.giraffe.user.SettingsLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceBindModule {

    @Binds
    @Singleton
    abstract fun bindKeywordsCacheCleaner(impl: KeywordsCacheCleanerImp): KeywordsCacheCleaner

    @Binds
    @Singleton
    abstract fun bindSettingsLocalDataSource(impl: SettingsLocalDataSourceImpl): SettingsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindLocalExploreDataSource(impl: LocalSearchDataSourceImpl): LocalSearchDataSource

    @Binds
    @Singleton
    abstract fun bindMoviesLocalDataSource(impl: MovieLocalDataSourceImp): MoviesLocalDataSource

    @Binds
    @Singleton
    abstract fun bindSeriesLocalDataSource(impl: SeriesRoomLocalDateSource): SeriesLocalDateSource

    @Binds
    @Singleton
    abstract fun bindPersonLocalDataSource(impl: PersonLocalDataSourceImp): PersonLocalDataSource

    @Binds
    @Singleton
    abstract fun bindOnboardingLocalDataSource(impl: OnboardingLocalDataSourceImpl): OnboardingLocalDataSource

    @Binds
    @Singleton
    abstract fun bindAuthenticationLocalDataSource(impl: AuthenticationLocalDataSourceImpl): AuthenticationLocalDataSource
}

