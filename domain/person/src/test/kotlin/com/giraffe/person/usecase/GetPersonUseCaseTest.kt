package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.Role
import com.giraffe.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetPersonUseCaseTest {
    private lateinit var repository: PersonRepository
    private lateinit var getPersonUseCase: GetPersonUseCase

    val fakePerson = Person(1, "Tarek", Role.Director, "url")
    val expectedPerson = Person(1, "Tarek", Role.Director, "url")

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getPersonUseCase = GetPersonUseCase(repository)
    }

    @Test
    fun `getById should call getPersonById on repository`() = runTest {
        coEvery { repository.getPersonById(1) } returns fakePerson

        getPersonUseCase.getById(1)

        coVerify(exactly = 1) { repository.getPersonById(1) }
    }

    @Test
    fun `getById should return correct person`() = runTest {
        coEvery { repository.getPersonById(1) } returns expectedPerson

        val result = getPersonUseCase.getById(1)

        assertThat(result).isEqualTo(expectedPerson)
    }

    @Test
    fun `getByName should call getPersonByName on repository`() = runTest {
        coEvery { repository.getPersonByName("Tarek") } returns fakePerson

        getPersonUseCase.getByName("Tarek")

        coVerify(exactly = 1) { repository.getPersonByName("Tarek") }
    }

    @Test
    fun `getByName should return correct person`() = runTest {
        coEvery { repository.getPersonByName("Tarek") } returns expectedPerson

        val result = getPersonUseCase.getByName("Tarek")

        assertThat(result).isEqualTo(expectedPerson)
    }
}