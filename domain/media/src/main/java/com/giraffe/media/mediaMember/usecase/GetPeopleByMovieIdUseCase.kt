package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class GetPeopleByMovieIdUseCase @Inject constructor(
    private val repository: MediaMemberRepository
) {
    suspend operator fun invoke(movieId: Int): MediaMemberRepository.MediaMembers {
        return repository.getMediaMembersByMovieId(movieId)
    }
}