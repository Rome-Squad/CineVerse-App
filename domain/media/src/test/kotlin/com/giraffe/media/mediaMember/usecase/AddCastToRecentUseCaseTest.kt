package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.createCastMember
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AddCastToRecentUseCaseTest {
    private lateinit var repository: MediaMemberRepository
    private lateinit var addCastToRecentCastUseCase: AddCastToRecentCastUseCase
    private val personToStore = createCastMember(1, "Tarek", "Acting")

    @BeforeEach
    fun setup() {
        repository = mockk()
        addCastToRecentCastUseCase = AddCastToRecentCastUseCase(repository)
    }

    @Test
    fun `invoke should call storePerson on repository with correct data`() = runTest {
        coEvery { repository.addCastToRecentViewed(any()) } returns Unit

        addCastToRecentCastUseCase(personToStore)

        coVerify(exactly = 1) { repository.addCastToRecentViewed(personToStore) }
    }
}