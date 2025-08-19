package com.giraffe.media.series.usecase.recentlyViewed

import com.giraffe.media.series.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SyncRecentlyViewedSeriesUseCaseTest {
    private val repository: SeriesRepository = mockk()
    private var useCase: SyncRecentlyViewedSeriesUseCase =
        SyncRecentlyViewedSeriesUseCase(repository)

    @Test
    fun `invoke should call syncRecentlyViewedSeries on repository`() = runTest {
        // given
        coEvery { repository.syncRecentlyViewedSeries() } returns Unit

        // when
        useCase.invoke()

        // then
        coVerify(exactly = 1) { repository.syncRecentlyViewedSeries() }
    }
}