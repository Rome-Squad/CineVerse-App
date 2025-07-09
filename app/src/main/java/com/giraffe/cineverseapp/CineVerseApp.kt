package com.giraffe.cineverseapp

import android.app.Application
import com.giraffe.cineverseapp.di.dataModule
import com.giraffe.cineverseapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CineVerseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CineVerseApp)
            modules(
                dataModule,
                networkModule
            )
        }
    }
}