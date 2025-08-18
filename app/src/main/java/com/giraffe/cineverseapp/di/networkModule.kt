package com.giraffe.cineverseapp.di

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.cineverseapp.data.network.createRetrofitClient
import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.retrofit.CollectionsApiService
import com.giraffe.media.collections.retrofit.CollectionsRemoteDataSourceImpl
import com.giraffe.media.match.datasource.MatchRemoteDataSource
import com.giraffe.media.match.retrofit.MatchApiService
import com.giraffe.media.match.retrofit.MatchRemoteDataSourceImpl
import com.giraffe.media.mediaMember.retrofit.MediaMemberApiService
import com.giraffe.media.mediaMember.retrofit.MediaMemberRemoteDataSourceImpl
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.retrofit.MoviesApiService
import com.giraffe.media.movie.retrofit.MoviesRemoteDataSourceImpl
import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.search.datasource.remote.SearchRemoteDataSource
import com.giraffe.media.search.retrofit.SearchApiService
import com.giraffe.media.search.retrofit.SearchRemoteDataSourceImpl
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.retrofit.SeriesApiService
import com.giraffe.media.series.retrofit.SeriesRemoteRetrofitDataSourceImpl
import com.giraffe.media.util.NetworkInterceptor
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
    fun provideMoviesApi(retrofit: Retrofit): MoviesApiService =
        retrofit.create(MoviesApiService::class.java)

    @Provides
    @Singleton
    fun provideSeriesApi(retrofit: Retrofit): SeriesApiService =
        retrofit.create(SeriesApiService::class.java)

    @Provides
    @Singleton
    fun provideExploreApi(retrofit: Retrofit): SearchApiService =
        retrofit.create(SearchApiService::class.java)

    @Provides
    @Singleton
    fun providePersonApi(retrofit: Retrofit): MediaMemberApiService =
        retrofit.create(MediaMemberApiService::class.java)

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
    fun provideCollectionsApi(retrofit: Retrofit): CollectionsApiService =
        retrofit.create(CollectionsApiService::class.java)

    @Provides
    @Singleton
    fun provideMoviesRemoteDataSource(api: MoviesApiService): MoviesRemoteDataSource =
        MoviesRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideSeriesRemoteDataSource(api: SeriesApiService): SeriesRemoteDataSource =
        SeriesRemoteRetrofitDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideExploreRemoteDataSource(api: SearchApiService): SearchRemoteDataSource =
        SearchRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun providePersonRemoteDataSource(api: MediaMemberApiService): MediaMemberRemoteDataSource =
        MediaMemberRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideCollectionsRemoteDataSource(api: CollectionsApiService): CollectionsRemoteDataSource =
        CollectionsRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideMatchRemoteDataSource(api: MatchApiService): MatchRemoteDataSource =
        MatchRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideAuthenticationRemoteDataSource(api: UserApiService): AuthenticationRemoteDataSource =
        AuthenticationRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(api: UserApiService): UserRemoteDataSource =
        UserRemoteDataSourceImpl(api)
}
