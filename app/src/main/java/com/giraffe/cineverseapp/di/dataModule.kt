package com.giraffe.cineverseapp.di

import androidx.room.Room
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.network.HttpClientFactory
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.series.SeriesRemoteDataSource
import com.giraffe.series.TmdbSeriesApiRemoteDataSource
import com.giraffe.series.api.BaseRequest
import com.giraffe.series.api.RequestBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


const val BASE_URL = "https://api.themoviedb.org/3/"

val dataModule = module {
    // Preferences
    single { DataStorePreferences(androidContext()) }

    // Database
    single { get<CineVerseDatabase>().movieDao() }
    single {
        Room.databaseBuilder(androidContext(), CineVerseDatabase::class.java, DATABASE_NAME).build()
    }

    // Network
    single {
        HttpClientFactory.create()
    }
    single {
        BaseRequest(BASE_URL)
    }
    singleOf(::RequestBuilder)

    // Data source
    single<SeriesRemoteDataSource> {
        TmdbSeriesApiRemoteDataSource(get(), get())
    }
}

private const val DATABASE_NAME = "CineVerseDataBase"