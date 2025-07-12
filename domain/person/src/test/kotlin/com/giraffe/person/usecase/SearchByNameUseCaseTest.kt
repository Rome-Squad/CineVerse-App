package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.exception.SearchResultNotFoundException
import com.giraffe.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class SearchByNameUseCaseTest {
    private lateinit var repository: PersonRepository
    private lateinit var searchPeopleByNameUseCase: SearchPeopleByNameUseCase

    val expectedList = listOf(
        Person(1, "Tarek", "Acting", "url")
    )

    @BeforeEach
    fun setup() {
        repository = mockk()
        searchPeopleByNameUseCase = SearchPeopleByNameUseCase(repository)
    }



    @Test
    fun `should returns list of people when repository returns search result`() = runTest {
        //given
        coEvery { repository.searchByName("Tarek") } returns expectedList
        //when
        val result = searchPeopleByNameUseCase("Tarek")
        //then
        assertThat(result).isEqualTo(expectedList)
    }
}