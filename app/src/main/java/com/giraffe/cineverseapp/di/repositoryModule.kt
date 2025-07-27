package com.giraffe.cineverseapp.di

import com.giraffe.media.explore.ExploreRepositoryImpl
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.explore.repository.ExploreRepository
import com.giraffe.media.movie.MoviesRepositoryImpl
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.person.PersonRepositoryImpl
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.repository.PersonRepository
import com.giraffe.media.series.SeriesRepositoryImpl
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.repository.AuthRepositoryImpl
import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.SessionManager
import com.giraffe.user.repository.AuthRepository
import com.google.firebase.sessions.dagger.Provides
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindExploreRepository(local: LocalExploreDataSource, remote: ExploreRemoteDataSource):
            ExploreRepository = ExploreRepositoryImpl(local, remote)

    @Provides
    @Singleton
    fun bindSeriesRepository(
        remote: SeriesRemoteDataSource,
        local: SeriesLocalDateSource,
        localExploreDataSource: LocalExploreDataSource
    ): SeriesRepository = SeriesRepositoryImpl(remote, local, localExploreDataSource)

    @Provides
    @Singleton
    fun bindMoviesRepository(
        local: MoviesLocalDataSource,
        remote: MoviesRemoteDataSource,
        sessionManager: SessionManager,
        localExploreDataSource: LocalExploreDataSource
    ): MoviesRepository =
        MoviesRepositoryImpl(local, remote, sessionManager, localExploreDataSource)

    @Provides
    @Singleton
    fun bindPersonRepository(
        remoteDataSource: PersonRemoteDataSource,
        localDataSource: PersonLocalDataSource,
        localExploreDataSource: LocalExploreDataSource,
    ): PersonRepository =
        PersonRepositoryImpl(remoteDataSource, localDataSource, localExploreDataSource)

    @Provides
    @Singleton
    fun bindAuthRepository(
        remoteDataSource: UserRemoteDataSource,
        sessionIdManager: SessionManager
    ): AuthRepository = AuthRepositoryImpl(remoteDataSource, sessionIdManager)
}
