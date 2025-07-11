package com.giraffe.cineverseapp.di

import androidx.room.Room
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.person.cleaner.PersonCacheCleaner
import com.giraffe.person.cleaner.PersonCacheCleanerImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { DataStorePreferences(androidContext()) }
    single {
        Room.databaseBuilder(androidContext(), CineVerseDatabase::class.java, DATABASE_NAME).build()
    }
    single<PersonCacheCleaner> { PersonCacheCleanerImp(get()) }
}

private const val DATABASE_NAME = "CineVerseDataBase"