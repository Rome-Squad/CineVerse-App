package com.giraffe.cineverseapp.di

import androidx.room.Room
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.cineverseapp.data.network.HttpClientFactory
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.person.PersonLocalDatasource
import com.giraffe.person.PersonLocalDatasourceImp
import com.giraffe.person.PersonRemoteDataSource
import com.giraffe.person.PersonRemoteDataSourceImp
import com.giraffe.person.PersonRepositoryImpl
import com.giraffe.person.repository.PersonRepository
import com.giraffe.person.util.ConnectionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { HttpClientFactory.create() }
    single { DataStorePreferences(androidContext()) }
    single { Room.databaseBuilder(androidContext(), CineVerseDatabase::class.java, DATABASE_NAME).build() }

    //region Movie
    single { get<CineVerseDatabase>().movieDao() }
    //endregion
    //region Person
    single { get<CineVerseDatabase>().personDao() }
    single { ConnectionChecker(androidContext()) }
    single<PersonRemoteDataSource> { PersonRemoteDataSourceImp(get()) }
    single<PersonLocalDatasource> { PersonLocalDatasourceImp(get()) }
    single<PersonRepository> { PersonRepositoryImpl(get(), get(), get()) }
    //endregion
}

private const val DATABASE_NAME = "CineVerseDataBase"