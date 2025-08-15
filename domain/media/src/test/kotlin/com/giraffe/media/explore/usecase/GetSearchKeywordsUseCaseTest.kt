package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.SearchRepository
import com.giraffe.media.explore.util.expected
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

    private lateinit var repository: SearchRepository
    private lateinit var useCase: GetSearchKeywordsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetSearchKeywordsUseCase(repository)
    }

    @Test
    fun `should return list of search keywords when query is valid`() = runTest {

        val rawQuery = "  trending now "
        val trimmedQuery = "trending now"

        coEvery { repository.getSearchKeywords(trimmedQuery) } returns flowOf(expected)

        val result = useCase(rawQuery).first()

        assertThat(result).isEqualTo(expected)
        coVerify { repository.getSearchKeywords(trimmedQuery) }
    }

    @Test
    fun `should call repository with trimmed query when query has leading or trailing spaces`() =
        runTest {

            val rawQuery = "  horror "
            val trimmedQuery = "horror"

            coEvery { repository.getSearchKeywords(trimmedQuery) } returns flowOf(emptyList())

            useCase(rawQuery).first()

            coVerify { repository.getSearchKeywords(trimmedQuery) }
        }

}