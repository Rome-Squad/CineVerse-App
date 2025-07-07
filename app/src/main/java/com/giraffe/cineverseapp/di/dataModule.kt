package com.giraffe.cineverseapp.di

import androidx.room.Room
import com.giraffe.cineverseapp.data.network.HttpClientFactory
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClientFactory.create()
    }
    single { DataStorePreferences(androidContext()) }
    single { get<CineVerseDatabase>().movieDao() }
    single {
        Room.databaseBuilder(androidContext(), CineVerseDatabase::class.java, DATABASE_NAME).build()
    }
}

private const val DATABASE_NAME = "CineVerseDataBase"