package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.createCastMember
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AddCastToRecentCastUseCaseTest {
    private val repository: MediaMemberRepository = mockk()
    private val addCastToRecentCastUseCase = AddCastToRecentCastUseCase(repository)

    @Test
    fun `invoke should call storePerson on repository with correct data`() = runTest {
        val castMember = createCastMember(1, "Tarek", "Acting")
        coEvery { repository.addCastToRecentViewed(any()) } returns Unit

        addCastToRecentCastUseCase(castMember)

        coVerify(exactly = 1) { repository.addCastToRecentViewed(castMember) }
    }
}