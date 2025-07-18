package com.giraffe.cineverseapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.media.movie.cleaner.MovieCacheCleaner
import com.giraffe.media.movie.cleaner.MovieCacheCleanerImp
import com.giraffe.media.person.cleaner.PersonCacheCleaner
import com.giraffe.media.person.cleaner.PersonCacheCleanerImp
import com.giraffe.media.series.cleaner.SeriesCacheCleaner
import com.giraffe.media.series.cleaner.SeriesCacheCleanerImp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single { DataStorePreferences(androidContext()) }
    single {
        Room.databaseBuilder(androidContext(), CineVerseDatabase::class.java, DATABASE_NAME).build()
    }
    singleOf(::PersonCacheCleanerImp) bind PersonCacheCleaner::class
    singleOf(::SeriesCacheCleanerImp) bind SeriesCacheCleaner::class
    singleOf(::MovieCacheCleanerImp) bind MovieCacheCleaner::class
}

const val DATABASE_NAME = "CineVerseDataBase"