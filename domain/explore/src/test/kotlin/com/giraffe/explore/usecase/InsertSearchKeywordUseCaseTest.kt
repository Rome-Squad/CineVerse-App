package com.giraffe.explore.usecase
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.repository.ExploreRepository
import com.giraffe.explore.utils.getCurrentLocalDateTime
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InsertSearchKeywordUseCaseTest {

    private lateinit var repository: ExploreRepository
    private lateinit var useCase: InsertSearchKeywordUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = InsertSearchKeywordUseCase(repository)
    }

    @Test
    fun `should call repository with given search keyword when execute is invoked`() = runTest {
        // Given
        val keyword = SearchKeyword(
            keyword = "sci-fi",
            isFromSearchHistory = false,
            lastSearchedTime = getCurrentLocalDateTime()
        )

        // When
        useCase.execute(keyword)

        // Then
        coVerify { repository.insertSearchKeyword(keyword) }
    }
}
