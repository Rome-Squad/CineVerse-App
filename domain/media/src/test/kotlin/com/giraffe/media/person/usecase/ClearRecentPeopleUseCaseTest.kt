package com.giraffe.media.person.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.ClearRecentPeopleUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class ClearRecentPeopleUseCaseTest {
    private lateinit var clearRecentPeopleUseCase: ClearRecentPeopleUseCase
    private val repository: MediaMemberRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        clearRecentPeopleUseCase = ClearRecentPeopleUseCase(repository)
    }

    @Test
    fun `should clear recent people when call ClearRecentPeopleUseCase`() = runTest {
        //given
        coEvery { repository.clearRecentViewed() } just Runs
        //when
        clearRecentPeopleUseCase()
        //then
        coVerify(exactly = 1) { clearRecentPeopleUseCase.invoke() }
    }

    @Test
    fun `should not proceed when repository throw Exception`() = runTest {
        //given
        coEvery { repository.clearRecentViewed() } throws Exception()
        //when && then
        assertThrows<Exception> { clearRecentPeopleUseCase() }
    }
}