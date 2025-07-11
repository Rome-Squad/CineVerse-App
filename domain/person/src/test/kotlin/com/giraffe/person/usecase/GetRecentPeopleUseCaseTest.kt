package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.Role
import com.giraffe.person.exception.RecentPeopleNotFoundException
import com.giraffe.person.repository.PersonRepository
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

    private val dummyPerson = Person(id = 5, name = "Mohannad", role = Role.Director)
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
    fun `should throw RecentPeopleNotFoundException when repository return empty list`() = runTest {
        //given
        coEvery { repository.getRecentPeople() } returns emptyList()
        //when && then
        assertThrows<RecentPeopleNotFoundException> { getRecentPeopleUseCase() }
    }

    @Test
    fun `should not proceed when repository throw Exception`() = runTest {
        //given
        coEvery { repository.getRecentPeople() } throws Exception()
        //when && then
        assertThrows<Exception> { getRecentPeopleUseCase() }
    }
}