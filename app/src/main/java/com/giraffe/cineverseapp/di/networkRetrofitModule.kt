package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.preference.SessionIdManagerImpl
import com.giraffe.media.explore.datasource.remote.ExploreRemoteDataSource
import com.giraffe.media.explore.retrofit.ExploreApiServiceRetrofit
import com.giraffe.media.explore.retrofit.ExploreRemoteDataSourceImplRetrofit
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.retrofit.MoviesApiServiceRetrofit
import com.giraffe.media.movie.retrofit.MoviesRemoteDataSourceImplRetrofit
import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.retrofit.PersonApiServiceRetrofit
import com.giraffe.media.person.retrofit.PersonRemoteDataSourceImplRetrofit
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.retrofit.SeriesApiServiceRetrofit
import com.giraffe.media.series.retrofit.SeriesRemoteRetrofitDataSourceImp
import com.giraffe.media.util.AuthInterceptor
import com.giraffe.media.util.RetrofitRequestBuilder
import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.SessionManager
import com.giraffe.user.retrofit.UserApiServiceRetrofit
import com.giraffe.user.retrofit.UserRemoteDataSourceImplRetrofit
import com.giraffe.user.util.RetrofitUserRequestBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "BASE_URL"
private const val ACCESS_TOKEN = "ACCESS_TOKEN"

val networkRetrofitModule = module {

    single(named(BASE_URL)) { BuildConfig.BASE_URL }
    single(named(ACCESS_TOKEN)) { BuildConfig.ACCESS_TOKEN }
    single { createRetrofitClient(get(named(ACCESS_TOKEN))) }
    single { Json { ignoreUnknownKeys = true; coerceInputValues = true } }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_URL)))
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(MoviesApiServiceRetrofit::class.java) }
    single { get<Retrofit>().create(SeriesApiServiceRetrofit::class.java) }
    single { get<Retrofit>().create(ExploreApiServiceRetrofit::class.java) }
    single { get<Retrofit>().create(PersonApiServiceRetrofit::class.java) }
    single { get<Retrofit>().create(UserApiServiceRetrofit::class.java) }

    single { RetrofitRequestBuilder(get<MoviesApiServiceRetrofit>()) }
    single { RetrofitRequestBuilder(get<SeriesApiServiceRetrofit>()) }
    single { RetrofitRequestBuilder(get<ExploreApiServiceRetrofit>()) }
    single { RetrofitRequestBuilder(get<PersonApiServiceRetrofit>()) }
    single { RetrofitUserRequestBuilder(get<UserApiServiceRetrofit>()) }

    single<MoviesRemoteDataSource> { MoviesRemoteDataSourceImplRetrofit(get()) }
    single<SeriesRemoteDataSource> { SeriesRemoteRetrofitDataSourceImp(get()) }
    single<ExploreRemoteDataSource> { ExploreRemoteDataSourceImplRetrofit(get()) }
    single<PersonRemoteDataSource> { PersonRemoteDataSourceImplRetrofit(get()) }
    single<UserRemoteDataSource> { UserRemoteDataSourceImplRetrofit(get()) }

    singleOf(::SessionIdManagerImpl) bind SessionManager::class
}

private fun createRetrofitClient(accessToken: String): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(accessToken))
        .addInterceptor(loggingInterceptor)
        .build()
}