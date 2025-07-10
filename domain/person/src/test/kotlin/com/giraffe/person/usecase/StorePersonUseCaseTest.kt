package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.Role
import com.giraffe.person.repository.PersonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class StorePersonUseCaseTest {
    private lateinit var repository: PersonRepository
    private lateinit var storePersonUseCase: StorePersonUseCase

    val personToStore = Person(1, "Tarek", Role.Story, "url")

    @BeforeEach
    fun setup() {
        repository = mockk()
        storePersonUseCase = StorePersonUseCase(repository)
    }

    @Test
    fun `invoke should call storePerson on repository with correct data`() = runTest {
        coEvery { repository.storePerson(any()) } returns Unit

        storePersonUseCase(personToStore)

        coVerify(exactly = 1) { repository.storePerson(personToStore) }
    }
}