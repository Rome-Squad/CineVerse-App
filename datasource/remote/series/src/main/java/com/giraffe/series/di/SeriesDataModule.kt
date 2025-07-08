package com.giraffe.series.di

import com.giraffe.series.BuildConfig
import com.giraffe.series.SeriesRemoteDataSource
import com.giraffe.series.TmdbSeriesApiRemoteDataSource
import com.giraffe.series.api.BaseRequest
import com.giraffe.series.api.RequestBuilder
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val seriesDataModule = module {

    single { BuildConfig.TMDB_API_KEY }

    singleOf(::BaseRequest)

    singleOf(::RequestBuilder)

    single<SeriesRemoteDataSource> {
        TmdbSeriesApiRemoteDataSource(get(), get())
    }
}