package com.giraffe.series.di

import com.giraffe.series.TmdbSeriesApiRemoteDataSource
import com.giraffe.series.api.BaseRequest
import com.giraffe.series.api.RequestBuilder
import com.giraffe.series.datasource.remote.SeriesRemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


val seriesRemoteDataModule = module {
    single {
        BaseRequest(
            baseURL = get(named("BASE_URL")),
            accessToken = get(named("ACCESS_TOKEN"))
        )
    }
    singleOf(::RequestBuilder)

    single<SeriesRemoteDataSource> {
        TmdbSeriesApiRemoteDataSource(get(), get())
    }
}