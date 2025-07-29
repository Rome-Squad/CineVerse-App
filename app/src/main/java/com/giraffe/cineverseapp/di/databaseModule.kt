package com.giraffe.cineverseapp.di

import android.content.Context
import androidx.room.Room
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.cineverseapp.data.preference.SessionIdManagerImpl
import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import com.giraffe.media.movie.cleaner.MovieCacheCleaner
import com.giraffe.media.movie.cleaner.MovieCacheCleanerImp
import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.person.cleaner.PersonCacheCleaner
import com.giraffe.media.person.cleaner.PersonCacheCleanerImp
import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.series.cleaner.SeriesCacheCleaner
import com.giraffe.media.series.cleaner.SeriesCacheCleanerImp
import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.user.SessionManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindPersonCacheCleaner(imp: PersonCacheCleanerImp): PersonCacheCleaner

    @Binds
    @Singleton
    abstract fun bindSeriesCacheCleaner(imp: SeriesCacheCleanerImp): SeriesCacheCleaner

    @Binds
    @Singleton
    abstract fun bindMovieCacheCleaner(imp: MovieCacheCleanerImp): MovieCacheCleaner

    @Binds
    @Singleton
    abstract fun bindSessionManager(imp: SessionIdManagerImpl): SessionManager

    companion object {
        @Provides
        @Singleton
        fun provideDataStorePreferences(@ApplicationContext context: Context): DataStorePreferences =
            DataStorePreferences(context)

        @Provides
        @Singleton
        fun provideExploreSearchKeywordDao(database: CineVerseDatabase): ExploreSearchKeywordDao =
            database.exploreSearchKeywordDao()

        @Provides
        @Singleton
        fun provideMovieDao(database: CineVerseDatabase): MovieDao = database.movieDao()

        @Provides
        @Singleton
        fun provideSeriesDao(database: CineVerseDatabase): SeriesDao = database.seriesDao()

        @Provides
        @Singleton
        fun providePersonDao(database: CineVerseDatabase): PersonDao = database.personDao()

        @Provides
        @Singleton
        fun provideCineVerseDatabase(@ApplicationContext context: Context): CineVerseDatabase =
            Room.databaseBuilder(
                context,
                CineVerseDatabase::class.java,
                "CineVerseDataBase"
            ).build()

    }
}
