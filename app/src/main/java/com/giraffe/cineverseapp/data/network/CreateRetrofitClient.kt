package com.giraffe.cineverseapp.data.network

import com.giraffe.cineverseapp.BuildConfig
import com.giraffe.media.util.AuthInterceptor
import com.giraffe.media.util.LanguageInterceptor
import com.giraffe.media.util.SessionIdInterceptor
import com.giraffe.media.util.SessionProvider
import com.giraffe.user.AuthenticationLocalDataSourceImp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

fun createRetrofitClient(
    accessToken: String,
    sessionProvider: SessionProvider
): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(accessToken))
        .addInterceptor(loggingInterceptor)
        .addInterceptor(LanguageInterceptor())
        .addInterceptor(SessionIdInterceptor(sessionProvider))
        .build()
}

inline fun <reified T> provideRetrofitService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}