package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.media.explore.LocalExploreDataSourceImpl
import com.giraffe.media.explore.cleaner.KeywordsCacheCleaner
import com.giraffe.media.explore.cleaner.KeywordsCacheCleanerImp
import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.movie.MovieLocalDataSourceImp
import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.person.PersonLocalDataSourceImp
import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.series.SeriesRoomLocalDateSource
import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import dagger.Binds
import dagger.Module
import dagger.Provides
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
    abstract fun bindLocalExploreDataSource(impl: LocalExploreDataSourceImpl): LocalExploreDataSource

    @Binds
    @Singleton
    abstract fun bindMoviesLocalDataSource(impl: MovieLocalDataSourceImp): MoviesLocalDataSource

    @Binds
    @Singleton
    abstract fun bindSeriesLocalDataSource(impl: SeriesRoomLocalDateSource): SeriesLocalDateSource

    @Binds
    @Singleton
    abstract fun bindPersonLocalDataSource(impl: PersonLocalDataSourceImp): PersonLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceProvideModule {

    @Provides
    @Singleton
    fun provideExploreSearchKeywordDao(database: CineVerseDatabase): ExploreSearchKeywordDao {
        return database.exploreSearchKeywordDao()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: CineVerseDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideSeriesDao(database: CineVerseDatabase): SeriesDao {
        return database.seriesDao()
    }

    @Provides
    @Singleton
    fun providePersonDao(database: CineVerseDatabase): PersonDao {
        return database.personDao()
    }
}
