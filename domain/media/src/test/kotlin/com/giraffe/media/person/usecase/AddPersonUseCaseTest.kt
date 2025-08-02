package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AddPersonUseCaseTest {
    private lateinit var repository: PersonRepository
    private lateinit var addRecentPersonUseCase: AddRecentPersonUseCase

    val personToStore = Person(1, "Tarek", "Acting")

    @BeforeEach
    fun setup() {
        repository = mockk()
        addRecentPersonUseCase = AddRecentPersonUseCase(repository)
    }

    @Test
    fun `invoke should call storePerson on repository with correct data`() = runTest {
        coEvery { repository.addRecentPerson(any()) } returns Unit

        addRecentPersonUseCase(personToStore)

        coVerify(exactly = 1) { repository.addRecentPerson(personToStore) }
    }
}