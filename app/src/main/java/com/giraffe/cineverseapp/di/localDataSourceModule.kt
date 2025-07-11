package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.explore.LocalExploreDataSourceImpl
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.series.SeriesRoomLocalDateSource
import com.giraffe.series.datasource.local.SeriesLocalDateSource
import com.giraffe.person.PersonLocalDataSourceImp
import com.giraffe.person.dao.PersonDao
import com.giraffe.person.local.PersonLocalDataSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single { get<CineVerseDatabase>().movieDao() }
    single<ExploreSearchKeywordDao> { get<CineVerseDatabase>().exploreSearchKeywordDao() }
    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }
    single { get<CineVerseDatabase>().seriesDao() }
    single<SeriesLocalDateSource> { SeriesRoomLocalDateSource(get()) }
    single<LocalExploreDataSource> { LocalExploreDataSourceImpl(get()) }
    single<PersonDao> { get<CineVerseDatabase>().personDao() }
    single<PersonLocalDataSource> { PersonLocalDataSourceImp(get()) }
}