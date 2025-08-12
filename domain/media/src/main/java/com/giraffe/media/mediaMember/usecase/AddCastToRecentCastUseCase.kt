package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class AddCastToRecentCastUseCase @Inject constructor(
    private val mediaMemberRepository: MediaMemberRepository
) {
    suspend operator fun invoke(castMember: CastMember) {
        mediaMemberRepository.addCastToRecentViewed(castMember)
    }
}