package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.explore.LocalExploreDataSourceImpl
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.series.SeriesRoomLocalDateSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single { get<CineVerseDatabase>().movieDao() }

    single<ExploreSearchKeywordDao> { get<CineVerseDatabase>().exploreSearchKeywordDao() }
    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }
    single { get<CineVerseDatabase>().seriesDao() }
    single { get<CineVerseDatabase>().searchCacheDao() }
    single { SeriesRoomLocalDateSource(get(), get()) }
}