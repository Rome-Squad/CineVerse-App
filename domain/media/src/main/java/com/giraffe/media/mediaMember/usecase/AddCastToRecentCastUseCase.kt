package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class AddCastToRecentCastUseCase @Inject constructor(
    private val repository: MediaMemberRepository
) {
    suspend operator fun invoke(castMember: CastMember) {
        repository.addCastToRecentViewed(castMember)
    }
}