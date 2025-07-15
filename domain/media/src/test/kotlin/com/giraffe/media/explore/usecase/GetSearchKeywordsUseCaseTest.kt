package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.repository.ExploreRepository
import com.giraffe.media.explore.utils.getCurrentLocalDateTime
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSearchKeywordsUseCaseTest {

    private lateinit var repository: ExploreRepository
    private lateinit var useCase: GetSearchKeywordsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetSearchKeywordsUseCase(repository)
    }

    @Test
    fun `should return list of search keywords when query is valid`() = runTest {
        // Given
        val rawQuery = "  trending now "
        val trimmedQuery = "trending now"
        val now = getCurrentLocalDateTime()

        val expected = listOf(
            SearchKeyword("trending now", isFromSearchHistory = false, lastSearchedTime = now),
            SearchKeyword("popular", isFromSearchHistory = true, lastSearchedTime = now)
        )

        coEvery { repository.getSearchKeywords(trimmedQuery) } returns flowOf(expected)

        // When
        val result = useCase(rawQuery).first()

        // Then
        assertThat(result).isEqualTo(expected)
        coVerify { repository.getSearchKeywords(trimmedQuery) }
    }

    @Test
    fun `should call repository with trimmed query when query has leading or trailing spaces`() =
        runTest {
            // Given
            val rawQuery = "  horror "
            val trimmedQuery = "horror"

            coEvery { repository.getSearchKeywords(trimmedQuery) } returns flowOf(emptyList())

            // When
            useCase(rawQuery).first() // Collect the flow to trigger the repository call

            // Then
            coVerify { repository.getSearchKeywords(trimmedQuery) }
        }

}