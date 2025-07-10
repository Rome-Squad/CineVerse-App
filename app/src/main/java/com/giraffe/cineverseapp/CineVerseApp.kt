package com.giraffe.cineverseapp

import android.app.Application
import com.giraffe.cineverseapp.di.dataModule
import com.giraffe.series.di.seriesDataModule
import com.giraffe.cineverseapp.di.localDataSourceModule
import com.giraffe.cineverseapp.di.networkModule
import com.giraffe.cineverseapp.di.repositoryModule
import com.giraffe.cineverseapp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CineVerseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CineVerseApp)
            modules(
                dataModule,
                localDataSourceModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                seriesDataModule
            )
        }
    }
}