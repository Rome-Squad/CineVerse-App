package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.network.HttpClientFactory
import com.giraffe.explore.RemoteExploreDataSourceImpl
import com.giraffe.explore.datasource.remote.RemoteExploreDataSource
import com.giraffe.series.datasource.remote.SeriesRemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.giraffe.series.api.BaseRequest
import com.giraffe.series.api.RequestBuilder
import com.giraffe.series.TmdbSeriesApiRemoteDataSource


val networkModule = module {

    single {
        HttpClientFactory.create()
    }
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

    single {
        BaseRequest(
        baseURL = get(named("BASE_URL")),
        accessToken = get(named("ACCESS_TOKEN"))
    )

    }

    single {
        RequestBuilder(
            httpClient = get()
        )
    }

    single<SeriesRemoteDataSource> {
        TmdbSeriesApiRemoteDataSource(
            requestBuilder = get(),
            baseRequest = get()
        )
    }
}