package com.giraffe.media.series.usecase

import com.giraffe.media.collections.util.createFakeSeries
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.user.entity.User
import com.giraffe.user.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetRatedSeriesUseCaseTest {

    private val repository: SeriesRepository = mockk(relaxed = true)
    private val getUserUseCase: GetUserUseCase = mockk(relaxed = true)
    private val useCase: GetRatedSeriesUseCase = GetRatedSeriesUseCase(repository, getUserUseCase)


    @Test
    fun `invoke should return rated series for user`() = runTest {
        val user = User(
            id = 123,
            displayName = "",
            username = "",
            avatarUrl = "",
        )
        val expectedResult = listOf(
            createFakeSeries(
                id = 1,
                name = "Breaking Bad"
            ),
            createFakeSeries(
                id = 2,
                name = "Better Call Saul"
            )
        )

        coEvery { getUserUseCase() } returns user
        coEvery { repository.getUserRated(user.id) } returns expectedResult

        val result = useCase()

        assertEquals(expectedResult, result)
    }
}
