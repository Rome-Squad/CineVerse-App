package com.giraffe.cineverseapp.di

import MovieDao
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.media.explore.LocalExploreDataSourceImpl
import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.person.PersonLocalDataSourceImp
import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.person.local.PersonLocalDataSource
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import  com.giraffe.media.movie.MovieLocalDataSourceImp
import  com.giraffe.media.movie.dao.MoviesSearchHistoryDao
import  com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.series.SeriesRoomLocalDateSource
import org.koin.dsl.module

val localDataSourceModule = module {

    single<ExploreSearchKeywordDao> { get<CineVerseDatabase>().exploreSearchKeywordDao() }
    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }

    single<MovieDao> { get<CineVerseDatabase>().movieDao() }
    single<MoviesSearchHistoryDao> { get<CineVerseDatabase>().moviesSearchHistoryDao() }
    single<MoviesLocalDataSource> { MovieLocalDataSourceImp(get()) }


    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }
    single { get<CineVerseDatabase>().seriesDao() }
    single<SeriesLocalDateSource> { SeriesRoomLocalDateSource(get()) }
    single<LocalExploreDataSource> { LocalExploreDataSourceImpl(get()) }
    single<PersonDao> { get<CineVerseDatabase>().personDao() }
    single<PersonLocalDataSource> { PersonLocalDataSourceImp(get()) }
}