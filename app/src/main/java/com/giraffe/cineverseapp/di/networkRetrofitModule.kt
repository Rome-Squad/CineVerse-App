package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.media.movie.MoviesApiServiceRetrofit
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSourceImplRetrofit
import com.giraffe.media.series.SeriesApiServiceRetrofit
import com.giraffe.media.series.SeriesRemoteRetrofitDataSourceImp
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.util.AuthInterceptor
import com.giraffe.media.util.RetrofitRequestBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "BASE_URL"
private const val ACCESS_TOKEN = "ACCESS_TOKEN"

val networkRetrofitModule = module {

    single(named(BASE_URL)) { BuildConfig.BASE_URL }
    single(named(ACCESS_TOKEN)) { BuildConfig.ACCESS_TOKEN }

    single {
        createRetrofitClient(get(named(ACCESS_TOKEN)))
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_URL)))
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { provideRetrofitService<MoviesApiServiceRetrofit>(get()) }
    single { provideRetrofitService<SeriesApiServiceRetrofit>(get()) }

    single<RetrofitRequestBuilder<MoviesApiServiceRetrofit>>(named("movies_builder")) {
        val api = get<Retrofit>().create(MoviesApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }

    single<RetrofitRequestBuilder<SeriesApiServiceRetrofit>>(named("series_builder")) {
        val api = get<Retrofit>().create(SeriesApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }

    single<MoviesRemoteDataSource> {
        MoviesRemoteDataSourceImplRetrofit(get(named("movies_builder")))
    }

    single<SeriesRemoteDataSource> {
        SeriesRemoteRetrofitDataSourceImp(get(named("series_builder")))
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
