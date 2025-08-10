package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.createCastMember
import com.giraffe.media.mediaMember.util.createCrewMember
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetMediaMembersByMovieIdUseCaseTest {

    private val repository: MediaMemberRepository = mockk()
    private val getMediaMembersByMovieIdUseCase = GetMediaMembersByMovieIdUseCase(repository)
    private val expectedCast = listOf(
        createCastMember(
            id = 1,
            name = "Christian Bale",
            role = "Acting",
            characterName = "Bruce Wayne / Batman",
            imageUrl = "https://image.tmdb.org/t/p/w500/christian_bale.jpg"
        ),
        createCastMember(
            id = 2,
            name = "Heath Ledger",
            role = "Acting",
            characterName = "The Joker",
            imageUrl = "https://image.tmdb.org/t/p/w500/heath_ledger.jpg"
        )
    )
    private val expectedCrew = listOf(
        createCrewMember(
            id = 1,
            name = "Christopher Nolan",
            role = "Director",
        )
    )
    private val expectedMediaMembers = MediaMemberRepository.MediaMembers(
        cast = expectedCast,
        crew = expectedCrew
    )

    @Test
    fun `should return list of people when repository returns people for movieId`() = runTest {
        // Given
        val movieId = 123
        coEvery { repository.getMediaMembersByMovieId(movieId) } returns expectedMediaMembers

        // When
        val result = getMediaMembersByMovieIdUseCase(movieId)

        // Then
        assertThat(expectedMediaMembers).isEqualTo(result)
    }
}