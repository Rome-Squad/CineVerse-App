package com.giraffe.media.person.usecase

import com.giraffe.media.person.repository.PersonRepository
import javax.inject.Inject

class GetPeopleMediaCreditsUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personId: Int) = repository.getPeopleMediaCredits(personId)
}