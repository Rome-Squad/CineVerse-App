package com.giraffe.explore.usecase

import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.repository.ExploreRepository
import com.giraffe.explore.utils.getCurrentLocalDateTime
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteSearchKeywordUseCaseTest {

    private lateinit var repository: ExploreRepository
    private lateinit var useCase: DeleteSearchKeywordUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteSearchKeywordUseCase(repository)
    }

    @Test
    fun `should call repository with given search keyword when execute is invoked`() = runTest {
        // Given
        val keyword = SearchKeyword(
            keyword = "sci-fi",
            isFromSearchHistory = true,
            lastSearchedTime = getCurrentLocalDateTime()
        )

        // When
        useCase(keyword)

        // Then
        coVerify { repository.deleteSearchKeyword(keyword) }
    }
}
