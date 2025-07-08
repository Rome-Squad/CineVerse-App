package com.giraffe.series.di

import android.app.Application
import androidx.room.Room
import com.giraffe.series.SeriesRoomLocalDateSource
import com.giraffe.series.database.SeriesDao
import com.giraffe.series.database.SearchCacheDao
import com.giraffe.series.database.SeriesDatabase
import org.koin.dsl.module

val seriesDatabaseModule = module {

    single {
        Room.databaseBuilder(
            get<Application>(),
            SeriesDatabase::class.java,
            "series_db"
        ).build()
    }

    single<SeriesDao> {
        get<SeriesDatabase>().seriesDao()
    }

    single<SearchCacheDao> {
        get<SeriesDatabase>().searchCacheDao()
    }
    single { SeriesRoomLocalDateSource(get(), get()) }

}
