package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AddSeriesRatingUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var addSeriesRatingUseCase: AddSeriesRatingUseCase

    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        addSeriesRatingUseCase = AddSeriesRatingUseCase(seriesRepository)
    }

    @Test
    fun `invoke should call addSeriesRating on repository with correct data`() = runTest {
        // given
        val seriesId = 1
        val ratingValue = 9.5f
        coEvery { seriesRepository.addSeriesRating(seriesId, ratingValue) } returns Unit

        // when
        addSeriesRatingUseCase(seriesId, ratingValue)

        // then
        coVerify(exactly = 1) { seriesRepository.addSeriesRating(seriesId, ratingValue) }
    }
}