package com.giraffe.cineverseapp.di

import android.content.Context
import androidx.room.Room
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.cineverseapp.data.preference.SessionIdManagerImpl
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun bindPersonCacheCleaner(dao: PersonDao): PersonCacheCleaner = PersonCacheCleanerImp(dao)

    @Provides
    @Singleton
    fun bindSeriesCacheCleaner(dao: SeriesDao): SeriesCacheCleaner = SeriesCacheCleanerImp(dao)

    @Provides
    @Singleton
    fun bindMovieCacheCleaner(dao: MovieDao): MovieCacheCleaner = MovieCacheCleanerImp(dao)

    @Provides
    @Singleton
    fun bindSessionManager(dataStorePreferences: DataStorePreferences): SessionManager =
        SessionIdManagerImpl(dataStorePreferences)


    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStorePreferences {
        return DataStorePreferences(context)
    }

    @Provides
    @Singleton
    fun provideCineVerseDatabase(@ApplicationContext context: Context): CineVerseDatabase {
        return Room.databaseBuilder(
            context,
            CineVerseDatabase::class.java,
            "CineVerseDataBase"
        ).build()
    }

}
