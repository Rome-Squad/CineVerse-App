package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.ExploreRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearSearchHistoryUseCaseTest {

    private lateinit var repository: ExploreRepository
    private lateinit var useCase: ClearSearchHistoryUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ClearSearchHistoryUseCase(repository)
    }

    @Test
    fun `should call repository to clear search history when execute is invoked`() = runTest {
        // When
        useCase()

        // Then
        coVerify { repository.clearSearchHistory() }
    }
}
