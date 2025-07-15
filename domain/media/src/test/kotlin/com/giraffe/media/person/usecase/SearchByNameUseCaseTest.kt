package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SearchByNameUseCaseTest {
    private lateinit var repository: PersonRepository
    private lateinit var searchPeopleByNameUseCase: SearchPeopleByNameUseCase

    val expectedList = listOf(
        Person(1, "Tarek", "Acting")
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