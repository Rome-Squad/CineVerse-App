package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.exception.MediaException
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.movie.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetCastCreditsUseCaseTest {

    private val repository: MediaMemberRepository = mockk()
    private val getCastCreditsUseCase: GetCastCreditsUseCase = GetCastCreditsUseCase(repository)
    private val personID = 1

    @Test
    fun `should return list of media credits when repository call success`() = runTest {
        val expectedMediaCredits = MediaMemberRepository.CastMedia(
            movies = listOf(
                Movie(
                    id = 1,
                    title = "Title",
                    rating = 8.5f,
                    description = "",
                    genresID = listOf(1, 2, 3),
                    popularity = 5f,
                    posterUrl = null,
                    duration = null,
                    backdropUrl = null,
                    releaseYear = null,
                    youtubeVideoId = null,
                    recentViewedAt = null,
                    recentReleasedAt = null,
                    upcomingAt = null,
                    userRating = null,
                )
            ),
            series = emptyList()
        )
        coEvery { repository.getCastCreditsById(personID) } returns expectedMediaCredits

        val result = getCastCreditsUseCase(personID)

        assertThat(result).isEqualTo(expectedMediaCredits)
    }

    @Test
    fun `should return empty list when person has no media credits`() = runTest {
        val expectedResult = MediaMemberRepository.CastMedia(
            movies = emptyList(),
            series = emptyList()
        )
        coEvery { repository.getCastCreditsById(personID) } returns expectedResult

        val result = getCastCreditsUseCase(personID)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should propagate MediaException when repository throws`() = runTest {
        coEvery { repository.getCastCreditsById(personID) } throws MediaException()

        assertThrows<MediaException> {
            getCastCreditsUseCase(personID)
        }
    }

}