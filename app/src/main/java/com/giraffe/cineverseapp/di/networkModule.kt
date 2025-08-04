package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.network.createRetrofitClient
import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.retrofit.CollectionsApiServiceRetrofit
import com.giraffe.media.collections.retrofit.CollectionsRemoteDataSourceImp
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
import com.giraffe.media.util.NetworkInterceptor
import com.giraffe.media.util.RetrofitRequestBuilder
import com.giraffe.repository.datasource.remote.AuthenticationRemoteDataSource
import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import com.giraffe.user.retrofit.AuthenticationRemoteDataSourceImpRetrofit
import com.giraffe.user.retrofit.UserApiServiceRetrofit
import com.giraffe.user.retrofit.UserRemoteDataSourceImplRetrofit
import com.giraffe.user.util.RetrofitUserRequestBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("BaseUrl")
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Named("AccessToken")
    @Singleton
    fun provideAccessToken(): String = BuildConfig.ACCESS_TOKEN

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("AccessToken") accessToken: String,
        authenticationDatastore: AuthenticationDatastore,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        return createRetrofitClient(
            accessToken = accessToken,
            authenticationDatastore = authenticationDatastore,
            networkInterceptor = networkInterceptor
        )
    }


    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("BaseUrl") baseUrl: String,
        json: Json,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApiServiceRetrofit =
        retrofit.create(MoviesApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun provideSeriesApi(retrofit: Retrofit): SeriesApiServiceRetrofit =
        retrofit.create(SeriesApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun provideExploreApi(retrofit: Retrofit): ExploreApiServiceRetrofit =
        retrofit.create(ExploreApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun providePersonApi(retrofit: Retrofit): PersonApiServiceRetrofit =
        retrofit.create(PersonApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApiServiceRetrofit =
        retrofit.create(UserApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun provideCollectionsApi(retrofit: Retrofit): CollectionsApiServiceRetrofit =
        retrofit.create(CollectionsApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun provideMoviesRequestBuilder(api: MoviesApiServiceRetrofit): RetrofitRequestBuilder<MoviesApiServiceRetrofit> =
        RetrofitRequestBuilder(api)

    @Provides
    @Singleton
    fun provideSeriesRequestBuilder(api: SeriesApiServiceRetrofit): RetrofitRequestBuilder<SeriesApiServiceRetrofit> =
        RetrofitRequestBuilder(api)

    @Provides
    @Singleton
    fun provideExploreRequestBuilder(api: ExploreApiServiceRetrofit): RetrofitRequestBuilder<ExploreApiServiceRetrofit> =
        RetrofitRequestBuilder(api)

    @Provides
    @Singleton
    fun providePersonRequestBuilder(api: PersonApiServiceRetrofit): RetrofitRequestBuilder<PersonApiServiceRetrofit> =
        RetrofitRequestBuilder(api)

    @Provides
    @Singleton
    fun provideUserRequestBuilder(api: UserApiServiceRetrofit): RetrofitUserRequestBuilder<UserApiServiceRetrofit> =
        RetrofitUserRequestBuilder(api)

    @Provides
    @Singleton
    fun provideCollectionsRequestBuilder(api: CollectionsApiServiceRetrofit): RetrofitRequestBuilder<CollectionsApiServiceRetrofit> =
        RetrofitRequestBuilder(api)

    @Provides
    @Singleton
    fun provideExploreRemoteDataSource(builder: RetrofitRequestBuilder<ExploreApiServiceRetrofit>): ExploreRemoteDataSource =
        ExploreRemoteDataSourceImplRetrofit(builder)

    @Provides
    @Singleton
    fun provideMoviesRemoteDataSource(builder: RetrofitRequestBuilder<MoviesApiServiceRetrofit>): MoviesRemoteDataSource =
        MoviesRemoteDataSourceImplRetrofit(builder)

    @Provides
    @Singleton
    fun providePersonRemoteDataSource(builder: RetrofitRequestBuilder<PersonApiServiceRetrofit>): PersonRemoteDataSource =
        PersonRemoteDataSourceImplRetrofit(builder)

    @Provides
    @Singleton
    fun provideSeriesRemoteDataSource(builder: RetrofitRequestBuilder<SeriesApiServiceRetrofit>): SeriesRemoteDataSource =
        SeriesRemoteRetrofitDataSourceImp(builder)

    @Provides
    @Singleton
    fun provideAuthenticationRemoteDataSource(
        builder: RetrofitUserRequestBuilder<UserApiServiceRetrofit>
    ): AuthenticationRemoteDataSource {
        return AuthenticationRemoteDataSourceImpRetrofit(builder)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(builder: RetrofitUserRequestBuilder<UserApiServiceRetrofit>): UserRemoteDataSource =
        UserRemoteDataSourceImplRetrofit(builder)

    @Provides
    @Singleton
    fun provideCollectionsRemoteDataSource(builder: RetrofitRequestBuilder<CollectionsApiServiceRetrofit>): CollectionsRemoteDataSource =
        CollectionsRemoteDataSourceImp(builder)
}