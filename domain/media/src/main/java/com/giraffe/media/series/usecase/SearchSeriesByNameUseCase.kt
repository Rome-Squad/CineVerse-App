package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository

class SearchSeriesByNameUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesName: String): List<Series> {

        val searchResults = seriesRepository.searchSeriesByName(seriesName)

        val sortedPreferences = seriesRepository.getSeriesGenres()

        if (sortedPreferences.isEmpty() || sortedPreferences.first().rank == 0) {
            return searchResults
        }

        val favoriteGenreId = sortedPreferences.first().id

        return searchResults.sortedByDescending { series ->
           series.genreIDs.contains(favoriteGenreId)
        }
    }
}