package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.createCastMember
import com.giraffe.media.mediaMember.util.createCrewMember
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetRecentMediaMembersUseCaseTest {
    private val repository: MediaMemberRepository = mockk(relaxed = true)
    private val getRecentMediaMembersUseCase = GetRecentMediaMembersUseCase(repository)
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
    fun `should get recent people when call GetRecentPeopleUseCase`() = runTest {
        //given
        coEvery { repository.getRecentMediaMembers() } returns expectedMediaMembers
        //when
        val result = getRecentMediaMembersUseCase()
        //then
        assertThat(result).isEqualTo(expectedMediaMembers)
    }


    @Test
    fun `should not proceed when repository throw Exception`() = runTest {
        //given
        coEvery { repository.getRecentMediaMembers() } throws Exception()
        //when && then
        assertThrows<Exception> { getRecentMediaMembersUseCase() }
    }
}