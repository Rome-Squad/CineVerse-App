package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class ClearRecentMediaMembersUseCase @Inject constructor(
    private val mediaMemberRepository: MediaMemberRepository
) {
    suspend operator fun invoke() = mediaMemberRepository.clearRecentViewed()
}