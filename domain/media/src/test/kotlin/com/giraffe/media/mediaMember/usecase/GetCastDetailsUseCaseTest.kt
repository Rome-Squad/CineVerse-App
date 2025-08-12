package com.giraffe.media.mediaMember.usecase


import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.TestDummyData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetCastDetailsUseCaseTest {
    private val repository: MediaMemberRepository = mockk()
    private val getCastDetailsUseCase = GetCastDetailsUseCase(repository)

    @Test
    fun `should return person details from repository`() = runTest {
        val personId = 3895
        val castDetails = TestDummyData.castMember1
        coEvery { repository.getCastDetailsById(any()) } returns castDetails

        val result = getCastDetailsUseCase(personId)

        assertThat(result).isEqualTo(castDetails)
    }
}