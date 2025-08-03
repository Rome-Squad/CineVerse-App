package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class SearchSeriesByNameUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesName: String, page: Int): List<Series> {
        val results = seriesRepository.searchSeriesByName(seriesName, page)
        val topGenre = seriesRepository.getSeriesGenres().firstOrNull { it.rank != 0 }
        return topGenre?.let { genre ->
            results.sortedByDescending { it.genreIDs.contains(genre.id) }
        } ?: results
    }
}