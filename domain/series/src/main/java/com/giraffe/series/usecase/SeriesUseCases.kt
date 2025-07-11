package com.giraffe.series.usecase

data class SeriesUseCases(
    val clearRecentSeries: ClearRecentSeries,
    val getRecentSeriesGenres: GetRecentSeriesGenres,
    val getSeriesGenres: GetSeriesGenresUseCase,
    val searchSeriesByName: SearchSeriesByNameUseCase,
    val storeRecentSeries: StoreRecentSeriesUseCase
)
