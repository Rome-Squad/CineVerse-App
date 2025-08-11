package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.TestDummyData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetCastMediaMembersBySeriesIdUseCaseTest {
    private val repository: MediaMemberRepository = mockk()
    private val getMediaMembersBySeriesIdUseCase = GetMediaMembersBySeriesIdUseCase(repository)

    @Test
    fun `should return list of people when repository returns people for showId`() = runTest {
        val showId = 93405
        val expectedMediaMembers = TestDummyData.mediaMembers
        coEvery { repository.getMediaMembersBySeriesId(showId) } returns expectedMediaMembers

        val result = getMediaMembersBySeriesIdUseCase(showId)

        assertThat(expectedMediaMembers).isEqualTo(result)
    }
}