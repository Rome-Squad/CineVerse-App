package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.network.HttpClientFactory
import com.giraffe.explore.RemoteExploreDataSourceImpl
import com.giraffe.explore.datasource.remote.RemoteExploreDataSource
import com.giraffe.movie.MoviesRemoteDataSourceImpl
import com.giraffe.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.person.PersonRemoteDataSourceImp
import com.giraffe.person.remote.PersonRemoteDataSource
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module


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

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single<RemoteExploreDataSource> {
        RemoteExploreDataSourceImpl(
            httpClient = get(),
            baseUrl = get(named("BASE_URL")),
            accessToken = get(named("ACCESS_TOKEN"))
        )
    }
    single<PersonRemoteDataSource> {
        PersonRemoteDataSourceImp(
            httpClient = get(),
            baseUrl = get(named("BASE_URL")),
            accessToken = get(named("ACCESS_TOKEN"))
        )
    }

    single<MoviesRemoteDataSource>{
        MoviesRemoteDataSourceImpl(
            client = get(),
            baseUrl = get(named("BASE_URL")),
            accessToken = get(named("ACCESS_TOKEN"))
        )
    }
}