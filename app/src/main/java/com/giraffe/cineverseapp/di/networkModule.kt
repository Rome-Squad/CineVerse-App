package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig.ACCESS_TOKEN
import com.giraffe.cineverseapp.BuildConfig.BASE_URL
import com.giraffe.cineverseapp.data.network.createRetrofitClient
import com.giraffe.cineverseapp.data.network.provideRetrofitService
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
import com.giraffe.media.util.RetrofitRequestBuilder
import com.giraffe.repository.datasource.local.AuthenticationRemoteDataSource
import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.cineverseapp.data.util.SessionProviderImpl
import com.giraffe.media.util.SessionProvider
import com.giraffe.user.retrofit.AuthenticationRemoteDataSourceImpRetrofit
import com.giraffe.user.retrofit.UserApiServiceRetrofit
import com.giraffe.user.retrofit.UserRemoteDataSourceImplRetrofit
import com.giraffe.user.util.RetrofitUserRequestBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val QUALIFIER_MOVIES_BUILDER = "movies_builder"
private const val QUALIFIER_SERIES_BUILDER = "series_builder"
private const val QUALIFIER_EXPLORE_BUILDER = "explore_builder"
private const val QUALIFIER_PERSON_BUILDER = "person_builder"
private const val QUALIFIER_USER_BUILDER = "user_builder"

val networkModule = module {

    single(named(BASE_URL)) { BASE_URL }
    single(named(ACCESS_TOKEN)) { ACCESS_TOKEN }
    single<SessionProvider> { SessionProviderImpl() }
    single { get<SessionProvider>() as SessionProviderImpl }

    single {
        createRetrofitClient(
            accessToken = get(named(ACCESS_TOKEN)),
            sessionProvider = get()
        )
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
    single { provideRetrofitService<UserApiServiceRetrofit>(get()) }

    single<RetrofitRequestBuilder<MoviesApiServiceRetrofit>>(named(QUALIFIER_MOVIES_BUILDER)) {
        val api = get<Retrofit>().create(MoviesApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }

    single<RetrofitRequestBuilder<SeriesApiServiceRetrofit>>(named(QUALIFIER_SERIES_BUILDER)) {
        val api = get<Retrofit>().create(SeriesApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }

    single<RetrofitRequestBuilder<ExploreApiServiceRetrofit>>(named(QUALIFIER_EXPLORE_BUILDER)) {
        val api = get<Retrofit>().create(ExploreApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }
    single<RetrofitUserRequestBuilder<UserApiServiceRetrofit>>(named(QUALIFIER_USER_BUILDER)) {
        val api = get<Retrofit>().create(UserApiServiceRetrofit::class.java)
        RetrofitUserRequestBuilder(api)
    }


    single<ExploreRemoteDataSource> {
        ExploreRemoteDataSourceImplRetrofit(get(named(QUALIFIER_EXPLORE_BUILDER)))
    }
    single<RetrofitRequestBuilder<PersonApiServiceRetrofit>>(named(QUALIFIER_PERSON_BUILDER)) {
        val api = get<Retrofit>().create(PersonApiServiceRetrofit::class.java)
        RetrofitRequestBuilder(api)
    }
    single<MoviesRemoteDataSource> {
        MoviesRemoteDataSourceImplRetrofit(get(named(QUALIFIER_MOVIES_BUILDER)))
    }
    single<PersonRemoteDataSource> {
        PersonRemoteDataSourceImplRetrofit(get(named(QUALIFIER_PERSON_BUILDER)))
    }
    single<SeriesRemoteDataSource> {
        SeriesRemoteRetrofitDataSourceImp(get(named(QUALIFIER_SERIES_BUILDER)))
    }
    single<UserRemoteDataSource> {
        UserRemoteDataSourceImplRetrofit(get(named(QUALIFIER_USER_BUILDER)))
    }

    single<AuthenticationRemoteDataSource> {
        AuthenticationRemoteDataSourceImpRetrofit(
            retrofitRequestBuilder = get(named(QUALIFIER_USER_BUILDER))
        )
    }
}