package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.repository.ExploreRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteSearchKeywordUseCaseTest {

    private lateinit var repository: ExploreRepository
    private lateinit var useCase: DeleteKeywordUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteKeywordUseCase(repository)
    }

    @Test
    fun `should call repository with given search keyword when execute is invoked`() = runTest {
        // Given
        val keyword = SearchKeyword(
            keyword = "sci-fi",
            isRecent = true,
            searchedAt = System.currentTimeMillis()
        )

        // When
        useCase(keyword.keyword)

        // Then
        coVerify { repository.deleteKeyword(keyword.keyword) }
    }
}
