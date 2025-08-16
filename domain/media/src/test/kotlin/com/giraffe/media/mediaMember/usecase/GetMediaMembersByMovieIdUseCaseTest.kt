package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.TestDummyData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetMediaMembersByMovieIdUseCaseTest {
    private val repository: MediaMemberRepository = mockk()
    private val getMediaMembersByMovieIdUseCase = GetMediaMembersByMovieIdUseCase(repository)

    @Test
    fun `should return list of people when repository returns people for movieId`() = runTest {
        val movieId = 123
        val mediaMembers = TestDummyData.mediaMembers
        coEvery { repository.getMediaMembersByMovieId(movieId) } returns mediaMembers

        val result = getMediaMembersByMovieIdUseCase(movieId)

        assertThat(result).isEqualTo(mediaMembers)
    }
}