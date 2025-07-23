package com.giraffe.media.person.usecase

import com.giraffe.media.entity.PagingData
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

    val expectedList = PagingData(
        List(15) { (Person(it, "Tarek", "Acting")) },
        15
    )

    @BeforeEach
    fun setup() {
        repository = mockk()
        searchPeopleByNameUseCase = SearchPeopleByNameUseCase(repository)
    }


    @Test
    fun `should returns list of people when repository returns search result`() = runTest {
        //given
        coEvery { repository.searchByName("Tarek", 1) } returns expectedList
        //when
        val result = searchPeopleByNameUseCase("Tarek", 1)
        //then
        assertThat(result).isEqualTo(expectedList)
    }
}