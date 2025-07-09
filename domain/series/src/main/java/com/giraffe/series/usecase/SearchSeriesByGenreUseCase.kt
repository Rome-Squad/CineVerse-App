package com.giraffe.series.usecase

import com.giraffe.series.entity.Series
import com.giraffe.series.repository.SeriesRepository

class SearchSeriesByGenreUseCase (
    private val seriesRepository: SeriesRepository
) {
    suspend fun searchSeriesByGenre(genreId: Int): Result<List<Series>> {
        return seriesRepository.searchSeriesByGenre(genreId)
    }
}