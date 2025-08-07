package com.giraffe.media.series.usecase

import com.giraffe.media.collections.fake.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.user.entity.User
import com.giraffe.user.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetRatedSeriesUseCaseTest {

    private lateinit var repository: SeriesRepository
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var useCase: GetRatedSeriesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getUserUseCase = mockk()
        useCase = GetRatedSeriesUseCase(repository, getUserUseCase)
    }

    @Test
    fun `invoke should return rated series for user`() = runTest {
        // Given
        val userId = 42
        val user = User(
            id = userId,
            displayName = "",
            username = "",
            avatarUrl = "",
        )
        val expectedResult = mapOf(
            8.5f to createFakeSeries(
                id = 1,
                name = "Breaking Bad"
            ),
            9.0f to createFakeSeries(
                id = 2,
                name = "Better Call Saul"
            )
        )

        coEvery { getUserUseCase() } returns user
        coEvery { repository.getRatedSeries(userId) } returns expectedResult

        // When
        val result = useCase()

        // Then
        coVerify { getUserUseCase() }
        coVerify { repository.getRatedSeries(userId) }
        assertEquals(expectedResult, result)
    }
}
