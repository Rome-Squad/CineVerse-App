package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class GetMediaMembersByMovieIdUseCase @Inject constructor(
    private val mediaMemberRepository: MediaMemberRepository
) {
    suspend operator fun invoke(movieId: Int): MediaMemberRepository.MediaMembers {
        return mediaMemberRepository.getMediaMembersByMovieId(movieId)
    }
}