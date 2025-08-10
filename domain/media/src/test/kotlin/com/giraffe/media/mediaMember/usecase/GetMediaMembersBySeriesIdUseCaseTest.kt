package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.createCastMember
import com.giraffe.media.mediaMember.util.createCrewMember
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetMediaMembersBySeriesIdUseCaseTest {

    private val repository: MediaMemberRepository = mockk()
    private val getMediaMembersBySeriesIdUseCase = GetMediaMembersBySeriesIdUseCase(repository)
    private val expectedCast = listOf(
        createCastMember(
            id = 10,
            name = "Lee Jung-jae",
            characterName = "Seong Gi-hun / Player 456",
            imageUrl = "https://image.tmdb.org/t/p/w500/3h5Cfm0X8ohWn7psZkqdNWqXAHH.jpg",
        ),
        createCastMember(
            id = 11,
            name = "Wi Ha-jun",
            characterName = "Detective Hwang Jun-ho",
            imageUrl = "https://image.tmdb.org/t/p/w500/tEZuIaMESdBw4LfNq3vshGR4VlP.jpg",
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
    fun `should return list of people when repository returns people for showId`() = runTest {
        // Given
        val showId = 93405
        coEvery { repository.getMediaMembersBySeriesId(showId) } returns expectedMediaMembers

        // When
        val result = getMediaMembersBySeriesIdUseCase(showId)

        // Then
        assertThat(expectedMediaMembers).isEqualTo(result)
    }
}