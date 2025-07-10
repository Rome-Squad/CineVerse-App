package com.giraffe.cineverseapp.di

import MovieDao
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.explore.LocalExploreDataSourceImpl
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.movie.MovieLocalDataSourceImp
import com.giraffe.movie.MoviesSearchHistoryDataSourceImpl
import com.giraffe.movie.dao.MoviesSearchHistoryDao
import com.giraffe.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.movie.datasource.local.MoviesSearchHistoryDataSource
import org.koin.dsl.module

val localDataSourceModule = module {

    single<ExploreSearchKeywordDao> { get<CineVerseDatabase>().exploreSearchKeywordDao() }
    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }

    single <MovieDao> { get<CineVerseDatabase>().movieDao() }
    single <MoviesSearchHistoryDao> { get<CineVerseDatabase>().moviesSearchHistoryDao() }
    single <MoviesSearchHistoryDataSource> { MoviesSearchHistoryDataSourceImpl(get()) }
    single<MoviesLocalDataSource>{ MovieLocalDataSourceImp(get()) }
}