package com.giraffe.media.series.usecase.genre

import com.giraffe.media.series.repository.SeriesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSeriesGenresByIdsUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(genreIDs: List<Int>) =
        seriesRepository.getGenresByIds(genreIDs).first()
}