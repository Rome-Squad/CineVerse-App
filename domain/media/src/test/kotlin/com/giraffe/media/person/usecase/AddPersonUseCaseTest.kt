package com.giraffe.media.person.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.AddRecentCastUseCase
import com.giraffe.media.person.entity.Person
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AddPersonUseCaseTest {
    private lateinit var repository: MediaMemberRepository
    private lateinit var addRecentCastUseCase: AddRecentCastUseCase

    val personToStore = Person(1, "Tarek", "Acting")

    @BeforeEach
    fun setup() {
        repository = mockk()
        addRecentCastUseCase = AddRecentCastUseCase(repository)
    }

    @Test
    fun `invoke should call storePerson on repository with correct data`() = runTest {
        coEvery { repository.addCastToRecentViewed(any()) } returns Unit

        addRecentCastUseCase(personToStore)

        coVerify(exactly = 1) { repository.addCastToRecentViewed(personToStore) }
    }
}