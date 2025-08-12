package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class GetMediaMembersByNameUseCase @Inject constructor(
    private val mediaMemberRepository: MediaMemberRepository
) {
    suspend operator fun invoke(personName: String, page: Int) =
        mediaMemberRepository.getActorByName(personName, page)
}