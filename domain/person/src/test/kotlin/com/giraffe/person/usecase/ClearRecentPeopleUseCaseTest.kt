package com.giraffe.person.usecase

import com.giraffe.person.repository.PersonRepository
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
    private val repository: PersonRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        clearRecentPeopleUseCase = ClearRecentPeopleUseCase(repository)
    }

    @Test
    fun `should clear recent people when call ClearRecentPeopleUseCase`() = runTest {
        //given
        coEvery { repository.clearRecentPeople() } just Runs
        //when
        clearRecentPeopleUseCase()
        //then
        coVerify(exactly = 1) { clearRecentPeopleUseCase.invoke() }
    }

    @Test
    fun `should not proceed when repository throw Exception`() = runTest {
        //given
        coEvery { repository.clearRecentPeople() } throws Exception()
        //when && then
        assertThrows<Exception> { clearRecentPeopleUseCase() }
    }
}