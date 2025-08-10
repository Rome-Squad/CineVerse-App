package com.giraffe.media.mediaMember.usecase

import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.person.entity.PersonCredit
import javax.inject.Inject

class GetPeopleMediaCreditsUseCase @Inject constructor(
    private val repository: MediaMemberRepository
) {
    suspend operator fun invoke(personId: Int): List<PersonCredit> {
        return emptyList<PersonCredit>()
//        repository.getMoviesAndSeriesById(personId)
    }
}