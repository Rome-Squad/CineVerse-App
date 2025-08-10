package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class GetPersonImagesUseCase @Inject constructor(
    private val repository: MediaMemberRepository
) {
    suspend operator fun invoke(personId: Int) = repository.getImagesUrlById(personId)
}