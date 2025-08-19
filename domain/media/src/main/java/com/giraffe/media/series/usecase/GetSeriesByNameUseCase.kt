package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesByNameUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesName: String, page: Int): List<Series> {
        val results = seriesRepository.getByName(name = seriesName, page = page)
        return seriesRepository.getTopGenreCount()?.let { genre ->
            results.sortedByDescending { genre.id in it.genreIDs }
        } ?: results
    }
}