package com.giraffe.media.series.usecase.genre

import com.giraffe.media.series.repository.SeriesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.giraffe.media.util.fakeGenres

class SyncSeriesGenresUseCaseTest {
    private var repository: SeriesRepository = mockk()
    private var useCase: SyncSeriesGenresUseCase = SyncSeriesGenresUseCase(repository)

    @Test
    fun `invoke should call getGenres on repository`() = runTest {
        // given
        coEvery { repository.getGenres() } returns emptyList()

        // when
        useCase.invoke()

        // then
        coVerify(exactly = 1) { repository.getGenres() }
    }

    @Test
    fun `invoke should return genres from repository`() = runTest {
        // given
        coEvery { repository.getGenres() } returns fakeGenres

        // when
        val result = useCase.invoke()

        // then
        assertThat(result).isEqualTo(fakeGenres)
    }
}