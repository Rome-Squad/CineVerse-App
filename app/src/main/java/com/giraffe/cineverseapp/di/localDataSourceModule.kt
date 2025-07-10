package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.explore.LocalExploreDataSourceImpl
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.movie.MovieLocalDataSourceImp
import com.giraffe.movie.datasource.local.MoviesLocalDataSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single { get<CineVerseDatabase>().movieDao() }

    single<ExploreSearchKeywordDao> { get<CineVerseDatabase>().exploreSearchKeywordDao() }
    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }

    single<MoviesLocalDataSource>{ MovieLocalDataSourceImp(get()) }
}