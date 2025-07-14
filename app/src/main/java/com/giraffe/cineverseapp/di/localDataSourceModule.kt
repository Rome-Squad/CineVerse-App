package com.giraffe.cineverseapp.di

import MovieDao
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.preference.SessionRepositoryImpl
import com.giraffe.explore.LocalExploreDataSourceImpl
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.datasource.local.LocalExploreDataSource
import com.giraffe.movie.MovieLocalDataSourceImp
import com.giraffe.movie.dao.MoviesSearchHistoryDao
import com.giraffe.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.movie.datasource.remote.SessionRepository
import com.giraffe.person.PersonLocalDataSourceImp
import com.giraffe.person.dao.PersonDao
import com.giraffe.person.local.PersonLocalDataSource
import com.giraffe.series.SeriesRoomLocalDateSource
import com.giraffe.series.datasource.local.SeriesLocalDateSource
import org.koin.dsl.module

val localDataSourceModule = module {

    single<ExploreSearchKeywordDao> { get<CineVerseDatabase>().exploreSearchKeywordDao() }
    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }

    single<MovieDao> { get<CineVerseDatabase>().movieDao() }
    single<MoviesSearchHistoryDao> { get<CineVerseDatabase>().moviesSearchHistoryDao() }
    single<MoviesLocalDataSource> { MovieLocalDataSourceImp(get()) }

    single<SessionRepository> { SessionRepositoryImpl(get()) }

    single<LocalExploreDataSource> {
        LocalExploreDataSourceImpl(get())
    }
    single { get<CineVerseDatabase>().seriesDao() }
    single<SeriesLocalDateSource> { SeriesRoomLocalDateSource(get()) }
    single<LocalExploreDataSource> { LocalExploreDataSourceImpl(get()) }
    single<PersonDao> { get<CineVerseDatabase>().personDao() }
    single<PersonLocalDataSource> { PersonLocalDataSourceImp(get()) }
}