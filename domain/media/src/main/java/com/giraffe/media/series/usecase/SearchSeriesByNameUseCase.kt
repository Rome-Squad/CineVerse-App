package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchSeriesByNameUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesName: String, page: Int): List<Series> {
        return withContext(Dispatchers.IO) {
            val searchResults = seriesRepository.searchSeriesByName(seriesName, page)

            val sortedPreferences = seriesRepository.getSeriesGenres()

            if (sortedPreferences.isEmpty() || sortedPreferences.first().rank == 0) {
                searchResults
            }

            val favoriteGenreId = sortedPreferences.first().id

            searchResults.sortedByDescending { series ->
                series.genreIDs.contains(favoriteGenreId)
            }
        }
    }
}