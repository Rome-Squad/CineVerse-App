package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.Role
import com.giraffe.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SearchByNameUseCaseTest {
    private lateinit var repository: PersonRepository
    private lateinit var searchByNameUseCase: SearchByNameUseCase

    val expectedList = listOf(
        Person(1, "Tarek", Role.ScreenPlay, "url")
    )

    @BeforeEach
    fun setup() {
        repository = mockk()
        searchByNameUseCase = SearchByNameUseCase(repository)
    }

    @Test
    fun `invoke should call searchByName on repository`() = runTest {
        coEvery { repository.searchByName(any()) } returns emptyList()

        searchByNameUseCase("Tarek")

        coVerify(exactly = 1) { repository.searchByName("Tarek") }
    }

    @Test
    fun `invoke should return list of persons from repository`() = runTest {
        coEvery { repository.searchByName("Tarek") } returns expectedList

        val result = searchByNameUseCase("Tarek")

        assertThat(result).isEqualTo(expectedList)
    }
}