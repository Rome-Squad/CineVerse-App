package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class ClearRecentPeopleUseCase @Inject constructor(
    private val repository: MediaMemberRepository
) {
    suspend operator fun invoke() = repository.clearRecentViewed()
}