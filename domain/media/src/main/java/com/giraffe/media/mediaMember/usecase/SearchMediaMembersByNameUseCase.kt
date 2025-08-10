package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import javax.inject.Inject

class SearchMediaMembersByNameUseCase @Inject constructor(
    private val repository: MediaMemberRepository
) {
    suspend operator fun invoke(personName: String, page: Int) =
        repository.searchForActorByName(personName, page)
}