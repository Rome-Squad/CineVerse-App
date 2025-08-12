package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.TestDummyData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetRecentCastMediaMembersUseCaseTest {
    private val repository: MediaMemberRepository = mockk(relaxed = true)
    private val getRecentMediaMembersUseCase = GetRecentMediaMembersUseCase(repository)

    @Test
    fun `should get recent people when call GetRecentPeopleUseCase`() = runTest {
        val expectedMediaMembers = TestDummyData.mediaMembers
        coEvery { repository.getRecentMediaMembers() } returns expectedMediaMembers

        val result = getRecentMediaMembersUseCase()

        assertThat(result).isEqualTo(expectedMediaMembers)
    }


    @Test
    fun `should not proceed when repository throw Exception`() = runTest {
        coEvery { repository.getRecentMediaMembers() } throws Exception()

        assertThrows<Exception> { getRecentMediaMembersUseCase() }
    }
}