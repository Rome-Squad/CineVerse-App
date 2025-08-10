package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.SearchRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ClearSearchHistoryUseCaseTest {

    private var repository: SearchRepository = mockk(relaxed = true)
    private var useCase: ClearSearchHistoryUseCase = ClearSearchHistoryUseCase(repository)

    @Test
    fun `should call repository to clear search history when execute is invoked`() = runTest {

    useCase()

        coVerify { repository.clearSearchHistory() }
    }
}
