package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.network.createRetrofitClient
import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.retrofit.CollectionsApiServiceRetrofit
import com.giraffe.media.collections.retrofit.CollectionsRemoteDataSourceImp
import com.giraffe.media.match.datasource.MatchRemoteDataSource
import com.giraffe.media.match.retrofit.MatchApiService
import com.giraffe.media.match.retrofit.MatchRemoteDataSourceImplRetrofit
import com.giraffe.media.mediaMember.retrofit.MediaMemberApiServiceRetrofit
import com.giraffe.media.mediaMember.retrofit.MediaMemberRemoteDataSourceImplRetrofit
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.retrofit.MoviesApiServiceRetrofit
import com.giraffe.media.movie.retrofit.MoviesRemoteDataSourceImplRetrofit
import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.search.datasource.remote.SearchRemoteDataSource
import com.giraffe.media.search.retrofit.SearchApiServiceRetrofit
import com.giraffe.media.search.retrofit.SearchRemoteDataSourceImplRetrofit
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.retrofit.SeriesApiServiceRetrofit
import com.giraffe.media.series.retrofit.SeriesRemoteRetrofitDataSourceImp
import com.giraffe.media.util.NetworkInterceptor
import com.giraffe.media.util.RetrofitRequestBuilder
import com.giraffe.repository.datasource.remote.AuthenticationRemoteDataSource
import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import com.giraffe.user.encryption.IEncryptionService
import com.giraffe.user.retrofit.AuthenticationRemoteDataSourceImpl
import com.giraffe.user.retrofit.UserApiService
import com.giraffe.user.retrofit.UserRemoteDataSourceImpl
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
        encryptionService: IEncryptionService,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        return createRetrofitClient(
            accessToken = accessToken,
            authenticationDatastore = authenticationDatastore,
            networkInterceptor = networkInterceptor,
            encryptionService = encryptionService
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
    fun provideExploreApi(retrofit: Retrofit): SearchApiServiceRetrofit =
        retrofit.create(SearchApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun providePersonApi(retrofit: Retrofit): MediaMemberApiServiceRetrofit =
        retrofit.create(MediaMemberApiServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideMatchApi(retrofit: Retrofit): MatchApiService =
        retrofit.create(MatchApiService::class.java)

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
    fun provideExploreRequestBuilder(api: SearchApiServiceRetrofit): RetrofitRequestBuilder<SearchApiServiceRetrofit> =
        RetrofitRequestBuilder(api)

    @Provides
    @Singleton
    fun providePersonRequestBuilder(api: MediaMemberApiServiceRetrofit): RetrofitRequestBuilder<MediaMemberApiServiceRetrofit> =
        RetrofitRequestBuilder(api)


    @Provides
    @Singleton
    fun provideAuthenticationRemoteDataSource(api: UserApiService): AuthenticationRemoteDataSource =
        AuthenticationRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(api: UserApiService): UserRemoteDataSource =
        UserRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideCollectionsRequestBuilder(api: CollectionsApiServiceRetrofit): RetrofitRequestBuilder<CollectionsApiServiceRetrofit> =
        RetrofitRequestBuilder(api)

    @Provides
    @Singleton
    fun provideExploreRemoteDataSource(builder: RetrofitRequestBuilder<SearchApiServiceRetrofit>): SearchRemoteDataSource =
        SearchRemoteDataSourceImplRetrofit(builder)

    @Provides
    @Singleton
    fun provideMoviesRemoteDataSource(builder: RetrofitRequestBuilder<MoviesApiServiceRetrofit>): MoviesRemoteDataSource =
        MoviesRemoteDataSourceImplRetrofit(builder)

    @Provides
    @Singleton
    fun providePersonRemoteDataSource(builder: RetrofitRequestBuilder<MediaMemberApiServiceRetrofit>): MediaMemberRemoteDataSource =
        MediaMemberRemoteDataSourceImplRetrofit(builder)

    @Provides
    @Singleton
    fun provideSeriesRemoteDataSource(builder: RetrofitRequestBuilder<SeriesApiServiceRetrofit>): SeriesRemoteDataSource =
        SeriesRemoteRetrofitDataSourceImp(builder)

    @Provides
    @Singleton
    fun provideCollectionsRemoteDataSource(builder: RetrofitRequestBuilder<CollectionsApiServiceRetrofit>): CollectionsRemoteDataSource =
        CollectionsRemoteDataSourceImp(builder)

    @Provides
    @Singleton
    fun provideMatchRemoteDataSource(
        api: MatchApiService
    ): MatchRemoteDataSource {
        return MatchRemoteDataSourceImplRetrofit(api)
    }

}