package com.giraffe.cineverseapp.di

import com.giraffe.media.collections.CollectionsRepositoryImp
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.explore.ExploreRepositoryImpl
import com.giraffe.media.explore.repository.ExploreRepository
import com.giraffe.media.movie.MovieRepositoryImpl
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.person.PersonRepositoryImpl
import com.giraffe.media.person.repository.PersonRepository
import com.giraffe.media.series.SeriesRepositoryImpl
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.repository.AuthenticationRepositoryImpl
import com.giraffe.repository.OnboardingRepositoryImpl
import com.giraffe.repository.SettingsRepositoryImpl
import com.giraffe.repository.UserRepositoryImpl
import com.giraffe.user.repository.AuthRepository
import com.giraffe.user.repository.OnboardingRepository
import com.giraffe.user.repository.SettingsRepository
import com.giraffe.user.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExploreRepository(impl: ExploreRepositoryImpl): ExploreRepository

    @Binds
    @Singleton
    abstract fun bindSeriesRepository(impl: SeriesRepositoryImpl): SeriesRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindPersonRepository(impl: PersonRepositoryImpl): PersonRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthenticationRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    @Singleton
    abstract fun bindCollectionsRepositoryRepository(impl: CollectionsRepositoryImp): CollectionsRepository
}