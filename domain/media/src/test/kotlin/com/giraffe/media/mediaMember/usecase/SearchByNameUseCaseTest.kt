package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.createCastMember
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SearchByNameUseCaseTest {
    private val repository: MediaMemberRepository = mockk()
    private val searchMediaMembersByNameUseCase = SearchMediaMembersByNameUseCase(repository)
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

    @Test
    fun `should returns list of casts when repository returns search result`() = runTest {
        //given
        coEvery { repository.searchForActorByName("Christian Bale", 1) } returns expectedCast
        //when
        val result = searchMediaMembersByNameUseCase("Christian Bale", 1)
        //then
        assertThat(result).isEqualTo(expectedCast)
    }
}