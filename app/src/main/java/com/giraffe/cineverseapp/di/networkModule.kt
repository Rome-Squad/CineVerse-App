package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.network.HttpClientFactory
import com.giraffe.media.explore.ExploreRemoteDataSourceImp
import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.movie.MoviesRemoteDataSourceImp
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.person.PersonRemoteDataSourceImp
import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.series.SeriesRemoteDataSourceImp
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.util.RequestBuilder
import com.giraffe.repository.SessionManagerImpl
import com.giraffe.repository.datasource.AuthRemoteDataSource
import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.AuthRemoteDataSourceImpl
import com.giraffe.user.SessionManager
import com.giraffe.user.UserRemoteDataSourceImpl
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


val networkModule = module {
    single<HttpClient> {
        HttpClientFactory.create()
    }
    single<String>(named(BASE_URL)) {
        BuildConfig.BASE_URL
    }
    single<String>(named(API_KEY)) {
        BuildConfig.API_KEY
    }
    single<String>(named(ACCESS_TOKEN)) {
        BuildConfig.ACCESS_TOKEN
    }
    single {
        RequestBuilder(
            client = get(),
            baseUrl = get(named(BASE_URL)),
            accessToken = get(named(ACCESS_TOKEN))
        )
    }
    single<UserRemoteDataSource> {
        UserRemoteDataSourceImpl(
            client = get(),
            baseUrl = get(named(BASE_URL)),
            accessToken = get(named(ACCESS_TOKEN))
        )
    }
    singleOf(::AuthRemoteDataSourceImpl) bind AuthRemoteDataSource::class
    singleOf(::ExploreRemoteDataSourceImp) bind ExploreRemoteDataSource::class
    singleOf(::MoviesRemoteDataSourceImp) bind MoviesRemoteDataSource::class
    singleOf(::SeriesRemoteDataSourceImp) bind SeriesRemoteDataSource::class
    singleOf(::PersonRemoteDataSourceImp) bind PersonRemoteDataSource::class
    singleOf(::SessionManagerImpl) bind SessionManager::class
}

private const val BASE_URL = "BASE_URL"
private const val API_KEY = "API_KEY"
private const val ACCESS_TOKEN = "ACCESS_TOKEN"