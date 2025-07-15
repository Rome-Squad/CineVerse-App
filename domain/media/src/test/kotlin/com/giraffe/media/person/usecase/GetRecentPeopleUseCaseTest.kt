package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetRecentPeopleUseCaseTest {
    private lateinit var getRecentPeopleUseCase: GetRecentPeopleUseCase
    private val repository: PersonRepository = mockk(relaxed = true)

    private val dummyPerson = Person(id = 5, name = "Mohannad", role = "Acting")
    private val dummyPeople = listOf(dummyPerson, dummyPerson, dummyPerson)

    @BeforeEach
    fun setup() {
        getRecentPeopleUseCase = GetRecentPeopleUseCase(repository)
    }

    @Test
    fun `should get recent people when call GetRecentPeopleUseCase`() = runTest {
        //given
        coEvery { repository.getRecentPeople() } returns dummyPeople
        //when
        val result = getRecentPeopleUseCase()
        //then
        assertThat(result.size).isEqualTo(3)
        assertThat(result.first()).isInstanceOf(Person::class.java)
    }



    @Test
    fun `should not proceed when repository throw Exception`() = runTest {
        //given
        coEvery { repository.getRecentPeople() } throws Exception()
        //when && then
        assertThrows<Exception> { getRecentPeopleUseCase() }
    }
}