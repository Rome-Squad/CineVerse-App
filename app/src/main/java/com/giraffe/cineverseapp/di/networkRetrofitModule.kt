package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
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
import com.giraffe.repository.SessionManagerImpl
import com.giraffe.repository.datasource.AuthRemoteDataSource
import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.AuthApiService
import com.giraffe.user.AuthRemoteDataSourceImpl
import com.giraffe.user.SessionManager
import com.giraffe.user.retrofit.UserApiServiceRetrofit
import com.giraffe.user.retrofit.UserRemoteDataSourceImplRetrofit
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

    single {
        createRetrofitClient(get(named(ACCESS_TOKEN)))
    }
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_URL)))
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .client(get())
            .build()
    }

    single { provideRetrofitService<MoviesApiServiceRetrofit>(get()) }
    single { provideRetrofitService<SeriesApiServiceRetrofit>(get()) }
    single { provideRetrofitService<ExploreApiServiceRetrofit>(get()) }
    single { provideRetrofitService<PersonApiServiceRetrofit>(get()) }

    single<RetrofitRequestBuilder<MoviesApiServiceRetrofit>>(named("movies_builder")) {
        val api = get<Retrofit>().create(MoviesApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }

    single<RetrofitRequestBuilder<SeriesApiServiceRetrofit>>(named("series_builder")) {
        val api = get<Retrofit>().create(SeriesApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }

    single<RetrofitRequestBuilder<ExploreApiServiceRetrofit>>(named("explore_builder")) {
        val api = get<Retrofit>().create(ExploreApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }
    single<RetrofitRequestBuilder<UserApiServiceRetrofit>>(named("user_builder")) {
        val api = get<Retrofit>().create(UserApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }


    single<ExploreRemoteDataSource> {
        ExploreRemoteDataSourceImplRetrofit(get(named("explore_builder")))
    }
    single<RetrofitRequestBuilder<PersonApiServiceRetrofit>>(named("person_builder")) {
        val api = get<Retrofit>().create(PersonApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }
    single<MoviesRemoteDataSource> {
        MoviesRemoteDataSourceImplRetrofit(get(named("movies_builder")))
    }
    single<PersonRemoteDataSource> {
        PersonRemoteDataSourceImplRetrofit(get(named("person_builder")))
    }
    single<SeriesRemoteDataSource> {
        SeriesRemoteRetrofitDataSourceImp(get(named("series_builder")))
    }

    single(named("user_retrofit")) {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .client(get())
            .build()
    }

    single<UserApiServiceRetrofit> {
        get<Retrofit>(named("user_retrofit")).create(UserApiServiceRetrofit::class.java)
    }

    single<UserRemoteDataSource> {
        UserRemoteDataSourceImplRetrofit(get())
    }
    singleOf(::SessionManagerImpl) bind SessionManager::class

    single<AuthRemoteDataSource>{
        AuthRemoteDataSourceImpl(get())
    }

    single <AuthApiService>{
        get<Retrofit>().create(AuthApiService::class.java)
    }

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

inline fun <reified T> provideRetrofitService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}
