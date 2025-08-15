package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetMediaMemberImageUrlsUseCaseTest {
    private val repository: MediaMemberRepository = mockk(relaxed = true)
    private val getMediaMemberImageUrlsUseCase = GetMediaMemberImageUrlsUseCase(repository)

    @Test
    fun `invoke should call repository to return image urls`() = runTest {
        val personId = 1

        getMediaMemberImageUrlsUseCase(personId)

        coVerify(exactly = 1) { repository.getImagesUrlById(personId) }
    }
}