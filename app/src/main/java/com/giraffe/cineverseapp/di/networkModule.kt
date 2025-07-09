package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.explore.RemoteExploreDataSourceImpl
import com.giraffe.explore.datasource.remote.RemoteExploreDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module


val networkModule = module {
    single<String>(named("BASE_URL")) {
        BuildConfig.BASE_URL
    }

    single<String>(named("API_KEY")) {
        BuildConfig.API_KEY
    }

    single<String>(named("ACCESS_TOKEN")) {
        BuildConfig.ACCESS_TOKEN
    }

    single<RemoteExploreDataSource> {
        RemoteExploreDataSourceImpl(
            httpClient = get(),
            baseUrl = get(named("BASE_URL")),
            accessToken = get(named("ACCESS_TOKEN"))
        )
    }
}