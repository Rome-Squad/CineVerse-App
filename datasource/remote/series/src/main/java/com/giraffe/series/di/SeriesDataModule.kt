package com.giraffe.media.series.di

import com.giraffe.media.series.TmdbSeriesApiRemoteDataSource
import com.giraffe.media.series.api.BaseRequest
import com.giraffe.media.series.api.DefaultRequestBuilder
import com.giraffe.media.series.api.RequestBuilder
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module


val seriesRemoteDataModule = module {
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
        TmdbSeriesApiRemoteDataSource(get(), get())
    }
}