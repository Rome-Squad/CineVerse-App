package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.explore.LocalExploreDataSourceImpl
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.person.PersonLocalDatasource
import com.giraffe.person.PersonLocalDatasourceImp
import com.giraffe.person.dao.PersonDao
import org.koin.dsl.module

val localDataSourceModule = module {
    single { get<CineVerseDatabase>().movieDao() }
    single<ExploreSearchKeywordDao> { get<CineVerseDatabase>().exploreSearchKeywordDao() }
    single<LocalExploreDataSource> { LocalExploreDataSourceImpl(get()) }
    single<PersonDao> { get<CineVerseDatabase>().personDao() }
    single<PersonLocalDatasource> { PersonLocalDatasourceImp(get()) }
}