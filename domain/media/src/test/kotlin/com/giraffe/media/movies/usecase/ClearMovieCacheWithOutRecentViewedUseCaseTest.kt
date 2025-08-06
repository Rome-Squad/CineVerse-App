package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ClearMovieCacheWithOutRecentViewedUseCaseTest {

    private lateinit var repository: MoviesRepository

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
    }


    @Test
    fun `should call clear movie cache without recent viewed from repository when invoke method`() = runTest {

        val useCase = ClearMovieCacheWithOutRecentViewedUseCase(repository)

        useCase()

        coVerify(exactly = 1) { repository.clearMovieCacheWithOutRecentViewed() }
    }
}