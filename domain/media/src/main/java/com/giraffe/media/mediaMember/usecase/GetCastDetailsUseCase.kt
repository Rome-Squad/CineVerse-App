package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class GetCastDetailsUseCase @Inject constructor(
    private val mediaMemberRepository: MediaMemberRepository
) {
    suspend operator fun invoke(castId: Int): CastMember {
        return mediaMemberRepository.getCastDetailsByid(castId)
    }
}