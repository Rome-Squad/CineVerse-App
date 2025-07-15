package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.network.HttpClientFactory
import com.giraffe.media.explore.RemoteExploreDataSourceImp
import com.giraffe.media.explore.datasource.remote.RemoteExploreDataSource
import com.giraffe.media.movie.MoviesRemoteDataSourceImp
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.person.PersonRemoteDataSourceImp
import com.giraffe.media.person.remote.PersonRemoteDataSource
import com.giraffe.media.series.SeriesRemoteDataSourceImp
import com.giraffe.media.series.api.BaseRequest
import com.giraffe.media.series.api.DefaultRequestBuilder
import com.giraffe.media.series.api.RequestBuilder
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.repository.SessionManagerImpl
import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.SessionManager
import com.giraffe.user.UserRemoteDataSourceImpl
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

    single<SessionManager> {
        SessionManagerImpl(get())
    }

    single<RemoteExploreDataSource> {
        RemoteExploreDataSourceImp(
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

    single<MoviesRemoteDataSource> {
        MoviesRemoteDataSourceImp(
            client = get(),
            baseUrl = get(named("BASE_URL")),
            accessToken = get(named("ACCESS_TOKEN"))
        )
    }

    single<UserRemoteDataSource> {
        UserRemoteDataSourceImpl(
            client = get(),
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

    single<RequestBuilder> {
        DefaultRequestBuilder(get())
    }

    single<SeriesRemoteDataSource> {
        SeriesRemoteDataSourceImp(get(), get())
    }
}