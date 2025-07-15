package com.giraffe.media.series.usecase

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearRecentSeriesTest {

    private lateinit var repository: SeriesRepository
    private lateinit var clearRecentSeriesUseCase: ClearRecentSeriesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        clearRecentSeriesUseCase = ClearRecentSeriesUseCase(repository)
    }

    @Test
    fun `Should call clearRecentSeries once`() = runTest {
        clearRecentSeriesUseCase()

        coVerify(exactly = 1) { repository.clearRecentSeries() }
    }
}