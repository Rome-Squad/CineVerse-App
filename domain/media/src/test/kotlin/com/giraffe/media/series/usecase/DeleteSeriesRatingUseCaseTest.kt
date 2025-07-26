package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class DeleteSeriesRatingUseCaseTest {
    private lateinit var seriesRepository: SeriesRepository
    private lateinit var deleteSeriesRatingUseCase: DeleteSeriesRatingUseCase

    @BeforeEach
    fun setUp() {
        seriesRepository = mockk()
        deleteSeriesRatingUseCase = DeleteSeriesRatingUseCase(seriesRepository)
    }

    @Test
    fun `invoke should call deleteSeriesRating on repository with correct id`() = runTest {
        // given
        val seriesId = 1
        coEvery { seriesRepository.deleteSeriesRating(seriesId) } returns Unit

        // when
        deleteSeriesRatingUseCase(seriesId)

        // then
        coVerify(exactly = 1) { seriesRepository.deleteSeriesRating(seriesId) }
    }
}