package com.giraffe.person.di

import android.app.Application
import androidx.room.Room
import com.giraffe.person.database.PersonDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val PersonDataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PersonDatabase::class.java,
            "person_database"
        ).build()
    }

    single {
        get<PersonDatabase>().personDao()
    }
}