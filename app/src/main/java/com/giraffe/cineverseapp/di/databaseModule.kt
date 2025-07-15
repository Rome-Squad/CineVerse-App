package com.giraffe.cineverseapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.movie.cleaner.MovieCacheCleaner
import com.giraffe.movie.cleaner.MovieCacheCleanerImp
import com.giraffe.person.cleaner.PersonCacheCleaner
import com.giraffe.person.cleaner.PersonCacheCleanerImp
import com.giraffe.series.cleaner.SeriesCacheCleaner
import com.giraffe.series.cleaner.SeriesCacheCleanerImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { DataStorePreferences(androidContext()) }
    single {
        Room.databaseBuilder(androidContext(), CineVerseDatabase::class.java, DATABASE_NAME).build()
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("CineVerse_Prefs", Context.MODE_PRIVATE)
    }

    single<PersonCacheCleaner> { PersonCacheCleanerImp(get()) }
    single<SeriesCacheCleaner> { SeriesCacheCleanerImp(get()) }
    single<MovieCacheCleaner> { MovieCacheCleanerImp(get()) }
}

const val DATABASE_NAME = "CineVerseDataBase"