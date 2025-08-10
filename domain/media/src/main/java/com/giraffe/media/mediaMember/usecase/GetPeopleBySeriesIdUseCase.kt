package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class GetPeopleBySeriesIdUseCase @Inject constructor(
    private val repository: MediaMemberRepository
) {
    suspend operator fun invoke(seriesId: Int): MediaMemberRepository.MediaMembers {
        return repository.getMediaMembersBySeriesId(seriesId)
    }
}