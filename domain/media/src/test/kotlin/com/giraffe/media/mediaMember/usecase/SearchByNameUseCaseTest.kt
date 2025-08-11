package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.TestDummyData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SearchByNameUseCaseTest {
    private val repository: MediaMemberRepository = mockk()
    private val searchMediaMembersByNameUseCase = SearchMediaMembersByNameUseCase(repository)

    @Test
    fun `should returns list of casts when repository returns search result`() = runTest {
        val expectedCast = TestDummyData.castMembers
        coEvery { repository.searchForActorByName(any(), 1) } returns expectedCast

        val result = searchMediaMembersByNameUseCase("Christian Bale", 1)

        assertThat(result).isEqualTo(expectedCast)
    }
}