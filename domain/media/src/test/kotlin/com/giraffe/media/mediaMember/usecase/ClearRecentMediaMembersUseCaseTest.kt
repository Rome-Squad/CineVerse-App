package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class ClearRecentMediaMembersUseCaseTest {
    private lateinit var clearRecentMediaMembersUseCase: ClearRecentMediaMembersUseCase
    private val repository: MediaMemberRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        clearRecentMediaMembersUseCase = ClearRecentMediaMembersUseCase(repository)
    }

    @Test
    fun `should clear recent people when call ClearRecentPeopleUseCase`() = runTest {
        //given
        coEvery { repository.clearRecentViewed() } just Runs
        //when
        clearRecentMediaMembersUseCase()
        //then
        coVerify(exactly = 1) { clearRecentMediaMembersUseCase.invoke() }
    }

    @Test
    fun `should not proceed when repository throw Exception`() = runTest {
        //given
        coEvery { repository.clearRecentViewed() } throws Exception()
        //when && then
        assertThrows<Exception> { clearRecentMediaMembersUseCase() }
    }
}